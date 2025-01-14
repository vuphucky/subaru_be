package com.example.quanlytaichinh_be.controller;

import com.example.quanlytaichinh_be.model.User;
import com.example.quanlytaichinh_be.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${user.upload.dir:uploads}")
    private String uploadBaseDir;

    private Path getUploadPath() {
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath.resolve(uploadBaseDir);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
            }
            
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error getting user profile: " + e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) MultipartFile avatar) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = userService.findByUsername(username);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
            }

            if (fullName != null && !fullName.trim().isEmpty()) {
                currentUser.setFullName(fullName);
            }
            
            if (email != null && !email.trim().isEmpty()) {
                currentUser.setEmail(email);
            }

            if (avatar != null && !avatar.isEmpty()) {
                // Get base upload directory
                Path uploadPath = getUploadPath();
                Path avatarsPath = uploadPath.resolve("avatars");
                
                // Create directories if they don't exist
                Files.createDirectories(avatarsPath);

                // Delete old avatar if exists
                if (currentUser.getAvatar() != null) {
                    try {
                        String oldAvatarPath = currentUser.getAvatar().replace("/uploads/", "");
                        Path oldFilePath = uploadPath.resolve(oldAvatarPath);
                        Files.deleteIfExists(oldFilePath);
                    } catch (IOException e) {
                        System.err.println("Error deleting old avatar: " + e.getMessage());
                    }
                }

                // Generate new filename and save
                String fileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
                Path filePath = avatarsPath.resolve(fileName);
                Files.copy(avatar.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Update avatar URL in user object
                String avatarUrl = "/uploads/avatars/" + fileName;
                currentUser.setAvatar(avatarUrl);
            }

            userService.save(currentUser);
            currentUser.setPassword(null);
            return ResponseEntity.ok(currentUser);
        } catch (IOException e) {
            e.printStackTrace(); // Log the full stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating user profile: " + e.getMessage());
        }
    }
}
