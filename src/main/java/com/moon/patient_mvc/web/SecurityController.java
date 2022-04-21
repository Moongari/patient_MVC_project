package com.moon.patient_mvc.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {



    // gestion des acces
    //creation d'un controller qui gere le fait de ne pas pouvoir acceder a certaines ressources du site.
    @GetMapping("/403")
    public String notAuthorize(){
        return "notAccessPage";
    }
    // gestion des acces
    //creation d'un controller qui gere le fait de ne pas pouvoir acceder a certaines ressources du site.
    @GetMapping("/404")
    public String notFound(){
        return "notAccessPage";
    }
}
