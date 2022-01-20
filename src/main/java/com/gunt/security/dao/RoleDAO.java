package com.gunt.security.dao;

import com.gunt.security.entity.Role;


public interface RoleDAO {
    void save(Role role);
    void delete(Role role);
    Role getById(Long id);
    Role getRoleByName(String rolename);
    Role createRoleIfNotFound(String name, long id);
}
