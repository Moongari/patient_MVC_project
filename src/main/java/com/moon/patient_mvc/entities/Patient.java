package com.moon.patient_mvc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;

    private boolean malade;

    private int score ;





}
