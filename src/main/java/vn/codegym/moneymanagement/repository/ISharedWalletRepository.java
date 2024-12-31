package vn.codegym.moneymanagement.repository;

import vn.codegym.moneymanagement.model.SharedWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISharedWalletRepository extends JpaRepository<SharedWallet, Long> {
}
