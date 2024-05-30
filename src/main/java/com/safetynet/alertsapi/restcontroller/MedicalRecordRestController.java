package com.safetynet.alertsapi.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;
import com.safetynet.alertsapi.repository.implementation.MedicalRecordRepositoryImpl;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordRestController {

    MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepositoryImpl();

    // ajouter un dossier médical pour une personne
    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalrecord(@RequestBody MedicalRecord newMedicalrecord) {
        MedicalRecord medicalRecord = medicalRecordRepository.save(newMedicalrecord);
        if (medicalRecord != null) {
            return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // mettre à jour un dossier médical existant par le nom et prénom de la personne
    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalrecord(@RequestBody MedicalRecord updateMedicalrecord) {
        MedicalRecord medicalRecordUpdated = medicalRecordRepository.update(updateMedicalrecord);
        if (medicalRecordUpdated != null) {
            return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // supprimer un dossier médical par le nom et prénom de la personne
    @DeleteMapping
    public ResponseEntity<MedicalRecord> deleteMedicalrecord(@RequestBody MedicalRecord deleteMedicalrecord) {
        boolean medicalrecordDeleted = medicalRecordRepository.delete(deleteMedicalrecord);
        if (medicalrecordDeleted == true) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
