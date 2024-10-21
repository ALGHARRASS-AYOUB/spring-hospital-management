package com.perso.hospitalthymeleaf.security.services;

import com.perso.hospitalthymeleaf.security.entities.Role;
import com.perso.hospitalthymeleaf.security.entities.User;

import java.util.ArrayList;


public interface SecurityService{
    public User saveNewUser(String firstname,String lastname, String username,
                            String email, String password, String confirmedPassword,
                            boolean isEnabled, boolean isTokenExpired);
    public Role saveNewRole(String roleName);
    public boolean grantRolesToUser(String username, String ...roles);
    public boolean grantRoleToUser(String username, String roleName);
    public boolean grantRolesToUser(String username, ArrayList<String> stringRoles);
    public boolean revokRoleFromeUser(String username, String roleName);


}
