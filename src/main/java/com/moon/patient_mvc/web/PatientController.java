package com.moon.patient_mvc.web;


import com.moon.patient_mvc.entities.Patient;
import com.moon.patient_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
// grace a lombock , il ajoute un constructeur avec parametre et crée l'injection de dependance
//ceci sans utiliser @Autowired
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;


    @GetMapping(path = "/index")
    public  String patient(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size){

        // il faut definir un retour non plus comme une liste mais comme une page.
        Page<Patient> pagePatient = patientRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listePatient",pagePatient.getContent());
        return "patients"; // retourne une vue de type patients.html
    }





}
