package com.example.quanlytaichinh_be.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "wallets")
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name= "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency; // Loại tiền tệ: VND, USD...

    private String description; // Mô tả ví

    private String iconUrl; // Đường dẫn lưu icon

//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Transaction> transactions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
//    private List<SharedWallet> sharedWallets = new ArrayList<>();

}
