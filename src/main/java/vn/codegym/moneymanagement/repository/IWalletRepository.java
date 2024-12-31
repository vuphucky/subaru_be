package vn.codegym.moneymanagement.repository;

import vn.codegym.moneymanagement.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long> {
}
