package com.safetynet.alertsapi.restcontroller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.ResponseEmailDTO;
import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.ResponsePersonInfoDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.service.ChildAlertService;
import com.safetynet.alertsapi.service.CommunityEmailService;
import com.safetynet.alertsapi.service.FireService;
import com.safetynet.alertsapi.service.FirestationService;
import com.safetynet.alertsapi.service.FloodService;
import com.safetynet.alertsapi.service.PersonInfoService;
import com.safetynet.alertsapi.service.PhoneAlertService;

@RestController
public class UrlRestController {

    private static final Logger logger = LogManager.getLogger(UrlRestController.class);

    @Autowired
    private CommunityEmailService communityEmailService;
    @Autowired
    private FirestationService firestationService;
    @Autowired
    private PhoneAlertService phoneAlertService;
    @Autowired
    private ChildAlertService childAlertService;
    @Autowired
    private FireService fireService;
    @Autowired
    private FloodService floodService;
    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("/firestation")
    public ResponseEntity<ResponseFirestationDTO> getPeopleByStationNumber(@RequestParam("stationNumber") String stationNumber) {
        ResponseFirestationDTO people = firestationService.getPersonsByStationNumber(stationNumber);

        if (people == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(people);
        return ResponseEntity.ok(people);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ResponseChildAlertDTO> getChildsByAddress(@RequestParam("address") String address) {
        ResponseChildAlertDTO childList = childAlertService.getChildsByAddress(address);

        if (childList == null) {
            logger.error("adress not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(childList);
        return ResponseEntity.ok(childList);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<ResponsePhoneAlertDTO> getPhonesByStationNumber(@RequestParam("firestation") String stationNumber) {
        ResponsePhoneAlertDTO phones = phoneAlertService.getPhonesByStationNumber(stationNumber);

        if (phones == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(phones);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/fire")
    public ResponseEntity<ResponseFireDTO> getPersonsByAdress(@RequestParam("address") String address) {
        ResponseFireDTO people = fireService.getPersonsByAdress(address);

        if (people == null) {
            logger.error("adress not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(people);
        return ResponseEntity.ok(people);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<ResponseFloodDTO>> getHouseholdInformationsByStations(@RequestParam("stations") List<Integer> stations) {
        List<ResponseFloodDTO> householdInformations = floodService.getHouseholdInformationsByStations(stations);

        if (householdInformations == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(householdInformations);
        return ResponseEntity.ok(householdInformations);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<ResponsePersonInfoDTO> getInformationsByName(
            @RequestParam("lastName") String lastName) {
        ResponsePersonInfoDTO person = personInfoService.getPersonInfoByName(lastName);

        if (person == null) {
            logger.error("person not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(person);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<ResponseEmailDTO> getPersonsByCity(@RequestParam("city") String city) {
        ResponseEmailDTO emails = communityEmailService.getAllEmailsByCity(city);

        if (emails == null) {
            logger.error("city not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(emails);
        return ResponseEntity.ok(emails);
    }
}
