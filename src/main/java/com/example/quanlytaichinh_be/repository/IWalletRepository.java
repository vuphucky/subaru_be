package com.example.quanlytaichinh_be.repository;

import com.example.quanlytaichinh_be.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long> {
}
