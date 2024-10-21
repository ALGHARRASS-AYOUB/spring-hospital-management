package com.perso.hospitalthymeleaf.security.repositories;

import com.perso.hospitalthymeleaf.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findUserByUsername(String username);
}
