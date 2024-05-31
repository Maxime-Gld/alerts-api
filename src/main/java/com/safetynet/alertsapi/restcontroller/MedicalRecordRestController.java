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

import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;
import com.safetynet.alertsapi.repository.implementation.MedicalRecordRepositoryImpl;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordRestController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordRestController.class);

    MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepositoryImpl();

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalrecord(@RequestBody MedicalRecord newMedicalrecord) {
        MedicalRecord medicalRecord = medicalRecordRepository.save(newMedicalrecord);
        if (medicalRecord != null) {
            logger.info("Medicalrecord created : " + medicalRecord);
            return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
        }
        logger.error("Medicalrecord not created : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalrecord(@RequestBody MedicalRecord updateMedicalrecord) {
        MedicalRecord medicalRecordUpdated = medicalRecordRepository.update(updateMedicalrecord);
        if (medicalRecordUpdated != null) {
            logger.info("Medicalrecord updated : " + medicalRecordUpdated);
            return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
        }
        logger.error("Medicalrecord not updated : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @DeleteMapping
    public ResponseEntity<MedicalRecord> deleteMedicalrecord(@RequestBody MedicalRecord deleteMedicalrecord) {
        boolean medicalrecordDeleted = medicalRecordRepository.delete(deleteMedicalrecord);
        if (medicalrecordDeleted == true) {
            logger.info("Medicalrecord deleted : " + deleteMedicalrecord);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.error("Medicalrecord not deleted : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
