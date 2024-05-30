package com.safetynet.alertsapi.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.repository.implementation.FirestationRepositoryImpl;

@RestController
@RequestMapping("/firestation")
public class FirestationRestController {

    FirestationRepository firestationRepository = new FirestationRepositoryImpl();

    // ajout d'un mapping caserne/adresse
    @PostMapping
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {
        Firestation newFirestation = firestationRepository.save(firestation);
        if (newFirestation != null) {
            return new ResponseEntity<>(newFirestation, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // mettre à jour le numéro de la caserne de pompiers d'une adresses
    @PutMapping
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation updatefirestation) {
        Firestation firestationUpdated = firestationRepository.update(updatefirestation);
        if (firestationUpdated != null) {
            return new ResponseEntity<>(firestationUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // supprimer le mapping d'une caserne ou d'une adresse.
    @DeleteMapping
    public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation deletefirestation) {
        boolean firestationDelested = firestationRepository.delete(deletefirestation);
        if (firestationDelested == true) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
