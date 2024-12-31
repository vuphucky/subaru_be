package com.example.quanlytaichinh_be.service.role;

import com.example.quanlytaichinh_be.model.Role;
import com.example.quanlytaichinh_be.model.enumM.RoleName;
import com.example.quanlytaichinh_be.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findByName(RoleName name) {
       return roleRepository.findByName(name);
    }
}
