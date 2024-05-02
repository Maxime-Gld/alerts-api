package com.safetynet.alertsapi.controller.restcontroller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonRestController {

    // ajouter une nouvelle personne
    @PostMapping
    public void addPerson() {

    }

    // modifier une personne par son nom et prénom
    @PutMapping
    public void updatePerson() {

    }

    // supprimer une personne par son nom et prénom
    @DeleteMapping
    public void deletePerson() {

    }
}
