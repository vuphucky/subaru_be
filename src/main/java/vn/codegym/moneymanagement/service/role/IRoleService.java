package vn.codegym.moneymanagement.service.role;

import vn.codegym.moneymanagement.model.Role;
import vn.codegym.moneymanagement.service.IGenericService;

public interface IRoleService {

        Role findByName(String name);

    }

