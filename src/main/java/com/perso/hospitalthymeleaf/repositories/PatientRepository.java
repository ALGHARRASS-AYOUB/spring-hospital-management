package com.perso.hospitalthymeleaf.repositories;

import com.perso.hospitalthymeleaf.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    public Page<Patient> findByName(String name,Pageable pageable);
    public Page<Patient> findByNameLike(String name,Pageable pageable);
    public Page<Patient> findByNameContains(String name,Pageable pageable);

}
