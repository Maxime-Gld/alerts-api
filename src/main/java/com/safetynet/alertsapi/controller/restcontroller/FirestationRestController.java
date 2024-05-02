package com.safetynet.alertsapi.controller.restcontroller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestation")
public class FirestationRestController {

    // ajout d'un mapping caserne/adresse
    @PostMapping
    public void getFirestation() {

    }

    // mettre à jour le numéro de la caserne de pompiers d'une adresse
    @PutMapping
    public void updateFirestation() {

    }

    // supprimer le mapping d'une caserne ou d'une adresse.
    @DeleteMapping
    public void deleteFirestation() {

    }

}
