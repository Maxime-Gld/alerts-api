package com.safetynet.alertsapi.controller.restcontroller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordRestController {

    // ajouter un dossier médical pour une personne
    @PostMapping
    public void getMedicalrecord() {

    }

    // mettre à jour un dossier médical existant par le nom et prénom de la personne
    @PutMapping
    public void updateMedicalrecord() {

    }

    // supprimer un dossier médical par le nom et prénom de la personne
    @DeleteMapping
    public void deleteMedicalrecord() {

    }
}
