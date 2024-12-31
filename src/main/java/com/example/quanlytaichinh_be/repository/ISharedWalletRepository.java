package com.example.quanlytaichinh_be.repository;

import com.example.quanlytaichinh_be.model.SharedWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISharedWalletRepository extends JpaRepository<SharedWallet, Long> {
}
