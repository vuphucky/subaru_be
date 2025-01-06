package com.example.quanlytaichinh_be.controller;
import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.model.User;
import com.example.quanlytaichinh_be.model.DTO.WalletDTO;
import com.example.quanlytaichinh_be.model.DTO.AddMoneyDTO;
import com.example.quanlytaichinh_be.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.quanlytaichinh_be.service.wallet.WalletService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
@CrossOrigin(origins = "*")
public class WalletController {
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addWallet(@RequestBody WalletDTO walletDTO) {
        try {
            // Kiểm tra tên ví
            if (walletDTO.getName() == null || walletDTO.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Tên ví không được để trống", HttpStatus.BAD_REQUEST);
            }

            // Lấy thông tin user từ token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            
            if (user == null) {
                return new ResponseEntity<>("Không tìm thấy thông tin người dùng", HttpStatus.NOT_FOUND);
            }

            // Tạo đối tượng Wallet mới từ DTO
            Wallet wallet = new Wallet();
            wallet.setName(walletDTO.getName());
            wallet.setBalance(walletDTO.getBalance() != null ? walletDTO.getBalance() : BigDecimal.ZERO);
            wallet.setIconUrl(walletDTO.getIconUrl() != null ? walletDTO.getIconUrl() : "AccountBalanceWallet");
            wallet.setCurrency(walletDTO.getCurrency() != null ? walletDTO.getCurrency() : "VND");
            wallet.setDescription(walletDTO.getDescription());
            wallet.setUser(user);
            wallet.setTotalDeposited(wallet.getBalance()); // Khởi tạo tổng tiền nạp bằng số dư ban đầu

            // Lưu ví
            Wallet savedWallet = walletService.save(wallet);
            return new ResponseEntity<>(savedWallet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi tạo ví: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fix")
    public ResponseEntity<?> updateWallet(@RequestBody WalletDTO walletDTO) {
        try {
            // Kiểm tra id ví
            if (walletDTO.getId() == null) {
                return new ResponseEntity<>("ID ví không được để trống", HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra tên ví
            if (walletDTO.getName() == null || walletDTO.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Tên ví không được để trống", HttpStatus.BAD_REQUEST);
            }

            // Tìm ví cần cập nhật
            Optional<Wallet> walletOptional = walletService.findById(walletDTO.getId());
            if (!walletOptional.isPresent()) {
                return new ResponseEntity<>("Không tìm thấy ví", HttpStatus.NOT_FOUND);
            }

            // Lấy thông tin user từ token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = userService.findByUsername(username);
            
            Wallet existingWallet = walletOptional.get();
            
            // Kiểm tra quyền sở hữu ví
            if (!existingWallet.getUser().getId().equals(currentUser.getId())) {
                return new ResponseEntity<>("Bạn không có quyền sửa ví này", HttpStatus.FORBIDDEN);
            }

            // Cập nhật thông tin ví
            existingWallet.setName(walletDTO.getName());
            existingWallet.setIconUrl(walletDTO.getIconUrl() != null ? walletDTO.getIconUrl() : existingWallet.getIconUrl());
            existingWallet.setCurrency(walletDTO.getCurrency() != null ? walletDTO.getCurrency() : existingWallet.getCurrency());
            existingWallet.setDescription(walletDTO.getDescription());

            // Xử lý cập nhật số dư
            if (walletDTO.getBalance() != null) {
                BigDecimal balanceChange = walletDTO.getBalance().subtract(existingWallet.getBalance());
                if (balanceChange.compareTo(BigDecimal.ZERO) > 0) {
                    // Nếu số dư mới lớn hơn số dư cũ, cập nhật tổng tiền đã nạp
                    existingWallet.setTotalDeposited(existingWallet.getTotalDeposited().add(balanceChange));
                }
                existingWallet.setBalance(walletDTO.getBalance());
            }

            // Lưu ví đã cập nhật
            Wallet updatedWallet = walletService.save(existingWallet);
            return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi cập nhật ví: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-money")
    public ResponseEntity<?> addMoneyToWallet(@RequestBody AddMoneyDTO addMoneyDTO) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (addMoneyDTO.getWalletId() == null) {
                return new ResponseEntity<>("ID ví không được để trống", HttpStatus.BAD_REQUEST);
            }

            if (addMoneyDTO.getAmount() == null || addMoneyDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return new ResponseEntity<>("Số tiền phải lớn hơn 0", HttpStatus.BAD_REQUEST);
            }

            // Tìm ví
            Optional<Wallet> walletOptional = walletService.findById(addMoneyDTO.getWalletId());
            if (!walletOptional.isPresent()) {
                return new ResponseEntity<>("Không tìm thấy ví", HttpStatus.NOT_FOUND);
            }

            // Lấy thông tin user từ token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = userService.findByUsername(username);
            
            if (currentUser == null) {
                return new ResponseEntity<>("Không tìm thấy thông tin người dùng", HttpStatus.NOT_FOUND);
            }
            
            Wallet wallet = walletOptional.get();
            
            // Kiểm tra quyền sở hữu ví
            if (!wallet.getUser().getId().equals(currentUser.getId())) {
                return new ResponseEntity<>("Bạn không có quyền thêm tiền vào ví này", HttpStatus.FORBIDDEN);
            }

            // Cập nhật số dư và tổng tiền đã nạp
            BigDecimal currentBalance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;
            BigDecimal currentTotalDeposited = wallet.getTotalDeposited() != null ? wallet.getTotalDeposited() : BigDecimal.ZERO;
            
            BigDecimal newBalance = currentBalance.add(addMoneyDTO.getAmount());
            BigDecimal newTotalDeposited = currentTotalDeposited.add(addMoneyDTO.getAmount());
            
            wallet.setBalance(newBalance);
            wallet.setTotalDeposited(newTotalDeposited);

            // Lưu ví đã cập nhật
            Wallet updatedWallet = walletService.save(wallet);
            
            return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Thêm log để debug
            return new ResponseEntity<>("Lỗi khi thêm tiền vào ví: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wallet>> findById(@PathVariable Long id) {
       Optional<Wallet> wallet = walletService.findById(id);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Wallet>> findAll() {
        Iterable<Wallet> walletList = walletService.findAll();
        return new ResponseEntity<>(walletList,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long id) {
        try {
            Optional<Wallet> wallet = walletService.findById(id);
            if (wallet.isPresent()) {
                walletService.delete(id);
                return new ResponseEntity<>("Xóa ví thành công", HttpStatus.OK);
            }
            return new ResponseEntity<>("Không tìm thấy ví", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi xóa ví: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
