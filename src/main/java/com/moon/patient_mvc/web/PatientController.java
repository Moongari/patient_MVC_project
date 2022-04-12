package com.moon.patient_mvc.web;


import com.moon.patient_mvc.entities.Patient;
import com.moon.patient_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
// grace a lombock , il ajoute un constructeur avec parametre et crée l'injection de dependance
//ceci sans utiliser @Autowired
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;


    @GetMapping(path = "/index")
    public  String patient(Model model){

        List<Patient> listePatient = patientRepository.findAll();
        model.addAttribute("listePatient",listePatient);
        return "patients"; // retourne une vue de type patients.html
    }





}
