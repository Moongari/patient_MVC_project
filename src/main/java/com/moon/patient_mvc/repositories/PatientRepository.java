package com.moon.patient_mvc.repositories;

import com.moon.patient_mvc.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient,Long> {
}
