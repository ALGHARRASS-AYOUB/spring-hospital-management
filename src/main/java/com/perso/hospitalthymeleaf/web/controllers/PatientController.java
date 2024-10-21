package com.perso.hospitalthymeleaf.web.controllers;

import com.perso.hospitalthymeleaf.entities.Patient;
import com.perso.hospitalthymeleaf.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/home";
    }

    @GetMapping("/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword
                           ){
        Pageable pageable= PageRequest.of(page,size);
        Page<Patient> patients;
        if(keyword.equals("")){
             patients = patientRepository.findAll(pageable);
        }else{
            String kw="%"+keyword+"%";
             patients = patientRepository.findByNameLike(kw,pageable);
        }

        model.addAttribute("patients",patients.getContent());
        model.addAttribute("currentPage",page);
        model.addAttribute("pages",new int[patients.getTotalPages()]);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "page",defaultValue = "0") int page,
                         @RequestParam(name = "size",defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword

    ){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }

    // return a json data in the context of Client Side rendering
    @GetMapping("/admin/patient-data")
    @ResponseBody

    public  List<Patient> patientsdata(){
        return patientRepository.findAll();
    }

    @GetMapping("/admin/newPatient")
    public String newPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "newPatientForm";
    }
    @PostMapping("/admin/addPatient")
    public String addPatient(Model model, @Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors())  return "/admin/newPatientForm";
        patientRepository.save(patient);
        return "redirect:/index";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model,
                       @RequestParam(name = "id") Long id,
                       @RequestParam(name = "page") int page,
                       @RequestParam(name = "keyword") String keyword
                       ){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null ) throw new RuntimeException("patient not found");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatientForm";
    }

    @PostMapping("/admin/editPatient")
    public String editPatient(Model model,
                              @Valid Patient patient,
                              BindingResult bindingResult,
                              @RequestParam(name = "page") int page,
                              @RequestParam(name = "keyword",defaultValue = "") String keyword
                            ){
        if (bindingResult.hasErrors())  return "editPatientForm";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

}
