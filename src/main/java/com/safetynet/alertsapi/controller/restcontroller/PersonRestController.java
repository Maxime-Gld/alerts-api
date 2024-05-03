package com.safetynet.alertsapi.controller.restcontroller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.PersonRepository;
import com.safetynet.alertsapi.repository.implementation.PersonRepositoryImpl;

@RestController
@RequestMapping("/person")
public class PersonRestController {

    PersonRepository personRepository = new PersonRepositoryImpl();

    // ajouter une nouvelle personne
    @PostMapping
    public void addPerson(@RequestBody Person newPerson) {
        personRepository.save(newPerson);
    }

    // modifier une personne par son nom et prénom
    @PutMapping
    public void updatePerson(@RequestBody Person updatePerson) {
        personRepository.update(updatePerson);
    }

    // supprimer une personne par son nom et prénom
    @DeleteMapping
    public void deletePerson(@RequestBody Person deletePerson) {
        personRepository.delete(deletePerson);
    }
}
