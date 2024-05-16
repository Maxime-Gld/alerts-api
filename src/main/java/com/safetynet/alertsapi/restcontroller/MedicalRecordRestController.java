package com.safetynet.alertsapi.restcontroller;

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
    public void addMedicalrecord(@RequestBody MedicalRecord newMedicalrecord) {
        medicalRecordRepository.save(newMedicalrecord);
    }

    // mettre à jour un dossier médical existant par le nom et prénom de la personne
    @PutMapping
    public void updateMedicalrecord(@RequestBody MedicalRecord updateMedicalrecord) {
        medicalRecordRepository.update(updateMedicalrecord);
    }

    // supprimer un dossier médical par le nom et prénom de la personne
    @DeleteMapping
    public void deleteMedicalrecord(@RequestBody MedicalRecord deleteMedicalrecord) {
        medicalRecordRepository.delete(deleteMedicalrecord);
    }
}
