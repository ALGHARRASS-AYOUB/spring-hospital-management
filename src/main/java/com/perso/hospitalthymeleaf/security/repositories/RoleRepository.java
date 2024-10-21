package com.perso.hospitalthymeleaf.security.repositories;

import com.perso.hospitalthymeleaf.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findRoleByName(String roleName);

}
