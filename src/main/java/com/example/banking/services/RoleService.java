package com.example.banking.services;

import com.example.banking.entities.Role;
import com.example.banking.entities.Privilege;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    Role updateRole(Long id, Role updatedRole);

    void deleteRole(Long id);

    List<Role> getAllRoles();

    Role addPrivilegeToRole(Long roleId, Privilege privilege);
}
