package com.example.quanlytaichinh_be.service.user;

import com.example.quanlytaichinh_be.config.UserPrinciple;
import com.example.quanlytaichinh_be.model.User;
import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return UserPrinciple.build(user);
    }

    public Wallet save(User user) {
        // You can add additional checks or preprocessing here if needed
        userRepository.save(user);
        return null;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public User findById_1(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }


    public User getUserById(Long userId) {
        // Find user by ID, throws an exception if not found
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByActivationToken(String token) {
        return userRepository.findByActivationToken(token);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changePassword(User user, String currentPassword, String newPassword) {
        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect.");
        }

        // Mã hóa mật khẩu mới
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        // Lưu người dùng với mật khẩu mới
        save(user);
    }

}
