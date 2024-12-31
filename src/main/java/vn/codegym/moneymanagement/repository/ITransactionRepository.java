package vn.codegym.moneymanagement.repository;

import vn.codegym.moneymanagement.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.wallet.id = :walletId")
    void deleteByWalletId(@Param("walletId") Long walletId);

}
