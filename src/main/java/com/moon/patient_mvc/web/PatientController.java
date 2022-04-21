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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
// grace a lombock , il ajoute un constructeur avec parametre et crée l'injection de dependance
//ceci sans utiliser @Autowired
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);


    @GetMapping(path = "/user/index")
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



    @GetMapping("/admin/delete")
    public String Delete( Long id,String motcle, int page){

        patientRepository.deleteById(id);
        log.info("suppression du patient dont l'id est {}",id);
        return "redirect:/user/index?page="+page+"&motcle="+motcle;
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


    @GetMapping("/about")
    public  String about()
    {
        return "about";
    }

    @GetMapping("/admin/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());

        return "formPatient";
    }


    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult result,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String motCle){



        if(result.hasErrors()){
            return "formPatient";
        }

        if(patient.getDateDeNaissance() == null){
            patient.setDateDeNaissance(new Date());
        }

        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&motcle="+motCle;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,Long id,String motCle, int page){

        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null){
            throw  new RuntimeException("Patient introuvable dans la base de données");
        }
        model.addAttribute("editPatient",patient);
        model.addAttribute("motcle" ,motCle);
        model.addAttribute("page",page);


        return "editPatient";
    }


    @GetMapping(path = "/")
    public  String home(){
        return "home";
    }



}
