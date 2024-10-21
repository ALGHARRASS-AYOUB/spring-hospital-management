package com.perso.hospitalthymeleaf;

import com.perso.hospitalthymeleaf.entities.Patient;
import com.perso.hospitalthymeleaf.repositories.PatientRepository;
import com.perso.hospitalthymeleaf.security.entities.User;
import com.perso.hospitalthymeleaf.security.services.SecurityService;
import com.perso.hospitalthymeleaf.security.services.SecurityServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalThymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return  args -> {
            Stream.of("ayoub","anase","rania","fadoua","salima","rida","ahlam","saida","sahar","raja","imane","raouya","raoua","saad","sara").forEach(name ->{
                patientRepository.save(new Patient(null, new Date(),name,Math.random()>0.5?true:false,100+Math.random()*100));
            });
        };
    }
    @Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("ayoub","algharrass","ayoub_admin","ayoub_admin@mail.com","password","password",true,false);
            securityService.saveNewUser("visitor","last","visitor","visitor@mail.com","password","password",true,false);
            securityService.saveNewUser("first","last","firstuser","firstuser@mail.com","password","password",true,false);
            securityService.saveNewUser("admin","last","admin","admin@mail.com","password","password",true,false);
            securityService.saveNewRole("ADMIN");
            securityService.saveNewRole("USER");
            securityService.saveNewRole("ADMINISTRATOR");
            securityService.saveNewRole("VISITOR");
            securityService.grantRoleToUser("visitor","VISITOR");
            securityService.grantRoleToUser("ayoub_admin","ADMINISTRATOR");
            securityService.grantRoleToUser("firstuser","USER");
            securityService.grantRoleToUser("firstuser","VISITOR");
            securityService.grantRoleToUser("admin","ADMIN");
            securityService.grantRoleToUser("admin","ADMINISTRATOR");
        };
    }
}
