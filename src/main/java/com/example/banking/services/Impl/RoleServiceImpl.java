package com.example.banking.services.Impl;

import com.example.banking.entities.Role;
import com.example.banking.entities.Privilege;
import com.example.banking.repository.RoleRepository;
import com.example.banking.repository.PrivilegeRepository;
import com.example.banking.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role updatedRole) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found."));
        existingRole.setName(updatedRole.getName());
        existingRole.setDescription(updatedRole.getDescription());
        existingRole.setPrivileges(updatedRole.getPrivileges());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role addPrivilegeToRole(Long roleId, Privilege privilege) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found."));
        Privilege existingPrivilege = privilegeRepository.findByName(privilege.getName());
        if (existingPrivilege == null) {
            existingPrivilege = privilegeRepository.save(privilege);
        }
        role.getPrivileges().add(existingPrivilege);
        return roleRepository.save(role);
    }
}
