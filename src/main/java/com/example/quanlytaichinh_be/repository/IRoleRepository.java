package com.example.quanlytaichinh_be.repository;

import com.example.quanlytaichinh_be.model.Role;
import com.example.quanlytaichinh_be.model.enumM.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
