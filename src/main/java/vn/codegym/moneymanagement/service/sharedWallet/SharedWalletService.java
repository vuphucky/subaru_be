package vn.codegym.moneymanagement.service.sharedWallet;

import vn.codegym.moneymanagement.model.SharedWallet;
import vn.codegym.moneymanagement.repository.ISharedWalletRepository;
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
    public void save(SharedWallet sharedWallet) {
        sharedWalletRepo.save(sharedWallet);
    }

    @Override
    public void delete(Long id) {
        sharedWalletRepo.deleteById(id);
    }
}
