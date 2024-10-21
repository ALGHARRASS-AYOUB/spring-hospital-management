package com.perso.hospitalthymeleaf.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    List<Role> roles =new ArrayList<Role>();


}
