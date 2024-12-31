package vn.codegym.moneymanagement.repository;

import vn.codegym.moneymanagement.model.Role;
import vn.codegym.moneymanagement.model.enumM.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
