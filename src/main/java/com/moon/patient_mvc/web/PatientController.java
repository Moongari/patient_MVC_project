package com.moon.patient_mvc.web;


import com.moon.patient_mvc.entities.Patient;
import com.moon.patient_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
// grace a lombock , il ajoute un constructeur avec parametre et crée l'injection de dependance
//ceci sans utiliser @Autowired
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);


    @GetMapping(path = "/index")
    public  String patient(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "motcle",defaultValue = "") String motcle){

        // il faut definir un retour non plus comme une liste mais comme une page.

        //on crée une nouvelle requete a laquelle on passe notre mot clé

        Page<Patient> pagePatient = patientRepository.findByNomContains(motcle,PageRequest.of(page,size));
        model.addAttribute("listePatient",pagePatient.getContent());

        //definit dans un tableau le nombre de pages Total
        model.addAttribute("pages", new int[pagePatient.getTotalPages()]);

        // on definit la page courante , la page cliqué devient la page courante
        model.addAttribute("current",page);

        model.addAttribute("motcle",motcle);

        log.info("Recherche du patient par mot cle : {}", motcle);

        return "patients"; // retourne une vue de type patients.html
    }



    @GetMapping("/delete")
    public String Delete( Long id,String motcle, int page){

        patientRepository.deleteById(id);
        log.info("suppression du patient dont l'id est {}",id);
        return "redirect:/index?page="+page+"&motcle="+motcle;
    }


    // Dans le cas ou je ne veux pas utiliser Thymeleaf cote serveur mais exposé mes données
    //cote client je peux creer la requete suivante tout en utilisant @Controller
    // je crée une api getMapping("/patients")
    //et je renvoie la reponse au format Json ceci pourra etre consommé par un application Client

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listeDesPatients(){

        return patientRepository.findAll();
    }



}
