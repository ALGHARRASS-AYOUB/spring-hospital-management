package com.perso.hospitalthymeleaf.security.services;

import com.perso.hospitalthymeleaf.security.entities.User;
import com.perso.hospitalthymeleaf.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findUserByUsername(username);
        if(user==null) throw new UsernameNotFoundException("user "+username+" not found");

        List<SimpleGrantedAuthority> roles=user.getRoles()==null? Collections.emptyList():user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        var springUser= new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
        System.out.println(springUser.getAuthorities());
    return  springUser;
    }
}
