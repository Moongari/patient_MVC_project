package com.moon.patient_mvc;

import com.moon.patient_mvc.entities.Patient;
import com.moon.patient_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientMvcApplication.class, args);
    }



   // @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){

        return args -> {

            patientRepository.save(new Patient(null,"Albert",new Date(),false, (int) (Math.random()*500)));
            patientRepository.save(new Patient(null,"John",new Date(),true,1344));
            patientRepository.save(new Patient(null,"Phil",new Date(),true,3421));
            patientRepository.save(new Patient(null,"Lucette",new Date(),true,3566));
            patientRepository.save(new Patient(null,"Theodore",new Date(),false,1345));
            patientRepository.save(new Patient(null,"Camille",new Date(),false,5776));

            patientRepository.findAll().forEach(patient -> {
                System.out.println(" Nom :"+ patient.getNom() +" === " +  " Malade :" + patient.isMalade());

            });

        };
    }

}
