package com.example.quanlytaichinh_be.service.wallet;

import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.repository.IWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService implements IWalletService {
    @Autowired
    private IWalletRepository walletRepository;

    @Override
    public Iterable<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Wallet save(Wallet wallet) {
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public void delete(Long id) {
        walletRepository.deleteById(id);
    }
}
