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

import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.repository.implementation.FirestationRepositoryImpl;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/firestation")
public class FirestationRestController {

    private static final Logger logger = LogManager.getLogger(FirestationRestController.class);

    FirestationRepository firestationRepository = new FirestationRepositoryImpl();


    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {
        Firestation newFirestation = firestationRepository.save(firestation);
        if (newFirestation != null) {
            logger.info("Firestation created : " + newFirestation);
            return new ResponseEntity<>(newFirestation, HttpStatus.CREATED);
        }
        logger.error("Firestation not created : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation updatefirestation) {
        Firestation firestationUpdated = firestationRepository.update(updatefirestation);
        if (firestationUpdated != null) {
            logger.info("Firestation updated : " + firestationUpdated);
            return new ResponseEntity<>(firestationUpdated, HttpStatus.OK);
        }
        logger.error("Firestation not updated : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @DeleteMapping
    public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation deletefirestation) {
        boolean firestationDelested = firestationRepository.delete(deletefirestation);
        if (firestationDelested == true) {
            logger.info("Firestation deleted : " + deletefirestation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.error("Firestation not deleted : BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
