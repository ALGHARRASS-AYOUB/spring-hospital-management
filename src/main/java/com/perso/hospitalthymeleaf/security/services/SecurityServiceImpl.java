package com.perso.hospitalthymeleaf.security.services;

import com.perso.hospitalthymeleaf.security.entities.Role;
import com.perso.hospitalthymeleaf.security.entities.User;
import com.perso.hospitalthymeleaf.security.repositories.RoleRepository;
import com.perso.hospitalthymeleaf.security.repositories.UserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@EnableTransactionManagement
public class SecurityServiceImpl implements SecurityService{
    UserRepository userRepository;
    RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public User saveNewUser(String firstname, String lastname, String username,
                            String email, String password, String confirmedPassword,
                            boolean isEnabled, boolean isTokenExpired) {
        if (userRepository.findUserByUsername(username)!=null)throw  new RuntimeException("user "+username+" already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("password re-entred does not match the actual password");
        User user =new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);
        String encryptedPassword=passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setEmail(email);
        user.setEnabled(isEnabled);
        user.setTokenExpired(isTokenExpired);
        User savedUser=userRepository.save(user);
        return savedUser;
    }

    @Override
    public Role saveNewRole(String roleName) {
        if(roleRepository.findRoleByName(roleName)!=null) throw new RuntimeException("role "+roleName+" already exists");
        Role role =new Role();
        role.setName(roleName);
        Role savedRole=roleRepository.save(role);
        return savedRole;
    }
    @Transactional
    @Override
    public boolean grantRolesToUser(String username, String ...roles) {
       User user=userRepository.findUserByUsername(username);
       if(user==null) throw new RuntimeException("user "+username+"not found for role granting");
        Arrays.stream(roles).forEach(roleName -> {
            Role role=roleRepository.findRoleByName(roleName);
            if(role==null) throw new RuntimeException("role "+roleName+" not found");
            user.getRoles().add(role);
            role.getUsers().add(user);
        });
        return true;
    }
    @Transactional
    @Override
    public boolean grantRoleToUser(String username, String roleName) {
        User user=userRepository.findUserByUsername(username);
        if(user==null) throw new RuntimeException("user "+username+"not found for role granting");
        Role role=roleRepository.findRoleByName(roleName);
        if(role==null) throw new RuntimeException("role "+roleName+" not found");
        user.getRoles().add(role);
        role.getUsers().add(user);
        return true;
    }
    @Transactional
    @Override
    public boolean grantRolesToUser(String username, ArrayList<String> stringRoles) {
        User user=userRepository.findUserByUsername(username);
        if(user==null) throw new RuntimeException("user "+username+"not found for role granting");
        stringRoles.forEach(roleName -> {
            Role role=roleRepository.findRoleByName(roleName);
            if(role==null) throw new RuntimeException("role "+roleName+" not found");
            user.getRoles().add(role);
            role.getUsers().add(user);
        });
        return true;
    }

    @Transactional
    @Override
    public boolean revokRoleFromeUser(String username, String roleName) {
        User user=userRepository.findUserByUsername(username);
        if(user==null) throw new RuntimeException("user "+username+"not found for role granting");
        Role role=roleRepository.findRoleByName(roleName);
        if(role==null) throw new RuntimeException("role "+roleName+" not found");
        user.getRoles().remove(role);
        role.getUsers().remove(user);
        return true;
    }
}
