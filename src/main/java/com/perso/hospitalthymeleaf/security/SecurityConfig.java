package com.perso.hospitalthymeleaf.security;

import com.perso.hospitalthymeleaf.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    UserDetailsServiceImpl fetchedUserDetails;
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(fetchedUserDetails);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .authorizeHttpRequests(auth->
                auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .requestMatchers("/index/**").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers("/webjars/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(f->f.defaultSuccessUrl("/index",true))
                .logout(
                        config-> config.logoutSuccessUrl("/home").permitAll());
        http.exceptionHandling(e->e.accessDeniedPage("/403"));
        //found that to clen up local storage we should add this config.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.CACHE, ClearSiteDataHeaderWriter.Directive.COOKIES, ClearSiteDataHeaderWriter.Directive.EXECUTION_CONTEXTS, ClearSiteDataHeaderWriter.Directive.STORAGE))) but it works maybe the problem was just in brave. it works fine in edge

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user= User.withDefaultPasswordEncoder()
//                .username("firstuser")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin= User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//        fetchedUserDetails.loadUserByUsername("admin");
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}
