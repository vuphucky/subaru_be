package vn.codegym.moneymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.codegym.moneymanagement.config.service.JwtResponse;
import vn.codegym.moneymanagement.config.service.JwtService;
import vn.codegym.moneymanagement.model.DTO.auth.ChangePasswordRequest;
import vn.codegym.moneymanagement.model.DTO.auth.ResetPasswordRequest;
import vn.codegym.moneymanagement.model.Role;
import vn.codegym.moneymanagement.model.User;
import vn.codegym.moneymanagement.model.enumM.RoleName;
import vn.codegym.moneymanagement.repository.IRoleRepository;
import vn.codegym.moneymanagement.service.email.EmailService;
import vn.codegym.moneymanagement.service.role.RoleService;
import vn.codegym.moneymanagement.service.user.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender emailSender;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt =jwtService.generateTokenLogin(authentication);
        UserDetails userDetails =(UserDetails)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        // Kiểm tra tài khoản đã kích hoạt hay chưa
        if (currentUser == null || !currentUser.isActive()) {
            return new ResponseEntity<>("Account is not activated. Please check your email to activate.", HttpStatus.FORBIDDEN);
        }



        return ResponseEntity.ok(new JwtResponse(currentUser.getId(), jwt, userDetails.getUsername(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Encrypt the password

        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        String pw = passwordEncoder.encode(user.getPassword());
        user.setPassword(pw);

        // Set default roles to "ROLE_USER"
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName(RoleName.ROLE_USER.toString());

        if (role != null) {
            roles.add(role);
        } else {
            return new ResponseEntity<>("Role not found", HttpStatus.BAD_REQUEST);
        }

        // Gán token kích hoạt
        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);

        // Mặc định chưa active
        user.setActive(true);

        user.setRoles(roles);




        // Save user to the database
        userService.save(user);
        // Gửi email kích hoạt
        String activationLink = "http://localhost:8080/api/auth/activate?token=" + activationToken;
        emailService.sendEmail(user.getEmail(), "Account Activation",
                "Click on the following link to activate your account: " + activationLink);

        return new ResponseEntity<>("Registration successful! Please check your email to activate your account.", HttpStatus.CREATED);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Đối với JWT, không cần thực hiện gì ở server, chỉ cần xóa token ở client
        return ResponseEntity.ok("Đăng xuất thành công!");
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam("token") String token) {
        User user = userService.findByActivationToken(token);

        if (user == null) {
            return new ResponseEntity<>("Invalid activation token", HttpStatus.BAD_REQUEST);
        }

        // Kích hoạt tài khoản
        user.setActive(true);
        user.setActivationToken(null); // Xóa token sau khi kích hoạt
        userService.save(user);

        return new ResponseEntity<>("Account activated successfully! You can now log in.", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        // Tìm user theo email
        String email = request.getEmail();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found!");
        }

        // Tạo mật khẩu mới
        String newPassword = PasswordGenerator.generateRandomPassword();

        // Mã hóa mật khẩu mới (nếu bạn đang sử dụng mã hóa mật khẩu)
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userService.save(user);  // Cập nhật mật khẩu mới vào cơ sở dữ liệu

        // Gửi email chứa mật khẩu mới
        sendResetPasswordEmail(user.getEmail(), newPassword);

        return ResponseEntity.ok("A new password has been sent to your email.");
    }

    private void sendResetPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kyvu.tsc@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Your new password is: " + newPassword);

        emailSender.send(message);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        // Lấy thông tin người dùng từ Authentication (đã đăng nhập)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Hoặc sử dụng user.getId() nếu bạn lưu thông tin id
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        try {
            userService.changePassword(user, request.getCurrentPassword(), request.getNewPassword());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Password updated successfully.");
    }


}
