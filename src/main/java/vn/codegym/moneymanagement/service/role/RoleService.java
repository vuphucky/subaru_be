package vn.codegym.moneymanagement.service.role;

import vn.codegym.moneymanagement.model.Role;
import vn.codegym.moneymanagement.model.enumM.RoleName;
import vn.codegym.moneymanagement.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository iRoleRepository;

    @Override
    public Role findByName(String name) {
        try {
            RoleName roleName = RoleName.valueOf(name); // Chuyển đổi String sang RoleName
            return iRoleRepository.findByName(roleName); // Gọi phương thức với RoleName
        } catch (IllegalArgumentException e) {
            // Xử lý nếu tên không hợp lệ
            return null; // Hoặc xử lý theo cách khác
        }
    }

}
