package com.example.quanlytaichinh_be.service.transaction;

import com.example.quanlytaichinh_be.model.Transaction;
import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Wallet save(Transaction transaction) {
        transactionRepository.save(transaction);
        return null;
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
    void deleteByWalletId(Long walletId){
        transactionRepository.deleteByWalletId(walletId);
    }
}
