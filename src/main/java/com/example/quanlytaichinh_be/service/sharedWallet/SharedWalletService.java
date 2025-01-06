package com.example.quanlytaichinh_be.service.sharedWallet;

import com.example.quanlytaichinh_be.model.SharedWallet;
import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.repository.ISharedWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SharedWalletService implements ISharedWalletService {
    @Autowired
    private ISharedWalletRepository sharedWalletRepo;

    @Override
    public Iterable<SharedWallet> findAll() {
        return sharedWalletRepo.findAll();
    }

    @Override
    public Optional<SharedWallet> findById(Long id) {
        return sharedWalletRepo.findById(id);
    }

    @Override
    public Wallet save(SharedWallet sharedWallet) {
        sharedWalletRepo.save(sharedWallet);
        return null;
    }

    @Override
    public void delete(Long id) {
        sharedWalletRepo.deleteById(id);
    }
}
