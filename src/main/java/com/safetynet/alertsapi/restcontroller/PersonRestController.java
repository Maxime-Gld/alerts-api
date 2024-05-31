package com.safetynet.alertsapi.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final Logger logger = LogManager.getLogger(PersonRestController.class);

    PersonRepository personRepository = new PersonRepositoryImpl();

    // ajouter une nouvelle personne
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person newPerson) {
        Person person = personRepository.save(newPerson);
        if (person != null) {
            logger.info("Person created : " + person);
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
        logger.error("Person not created : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // modifier une personne par son nom et prénom
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatePerson) {
        Person personUpdated = personRepository.update(updatePerson);
        if (personUpdated != null) {
            logger.info("Person updated : " + personUpdated);
            return new ResponseEntity<>(personUpdated, HttpStatus.OK);
        }
        logger.error("Person not updated : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // supprimer une personne par son nom et prénom
    @DeleteMapping
    public ResponseEntity<Person> deletePerson(@RequestBody Person deletePerson) {
        boolean personDeleted = personRepository.delete(deletePerson);
        if (personDeleted == true) {
            logger.info("Person deleted : " + deletePerson);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.error("Person not deleted : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
