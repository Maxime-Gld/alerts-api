package com.safetynet.alertsapi.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.config.constant.EndPointConstant;
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

    private static final Logger logger = LoggerFactory.getLogger(UrlRestController.class);

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

    @GetMapping(EndPointConstant.FIRESTATION)
    public ResponseEntity<ResponseFirestationDTO> getPeopleByStationNumber(@RequestParam("stationNumber") String stationNumber) {
        ResponseFirestationDTO people = firestationService.getPersonsByStationNumber(stationNumber);

        if (people == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(people.toString());
        return ResponseEntity.ok(people);
    }

    @GetMapping(EndPointConstant.CHILD)
    public ResponseEntity<ResponseChildAlertDTO> getChildsByAddress(@RequestParam("address") String address) {
        ResponseChildAlertDTO childList = childAlertService.getChildsByAddress(address);

        if (childList == null) {
            logger.error("address not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(childList.toString());
        return ResponseEntity.ok(childList);
    }

    @GetMapping(EndPointConstant.PHONE)
    public ResponseEntity<ResponsePhoneAlertDTO> getPhonesByStationNumber(@RequestParam("firestation") String stationNumber) {
        ResponsePhoneAlertDTO phones = phoneAlertService.getPhonesByStationNumber(stationNumber);

        if (phones == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(phones.toString());
        return ResponseEntity.ok(phones);
    }

    @GetMapping(EndPointConstant.FIRE)
    public ResponseEntity<ResponseFireDTO> getPersonsByAdress(@RequestParam("address") String address) {
        ResponseFireDTO people = fireService.getPersonsByAdress(address);

        if (people == null) {
            logger.error("address not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(people.toString());
        return ResponseEntity.ok(people);
    }

    @GetMapping(EndPointConstant.FLOOD)
    public ResponseEntity<List<ResponseFloodDTO>> getHouseholdInformationsByStations(@RequestParam("stations") List<Integer> stations) {
        List<ResponseFloodDTO> householdInformations = floodService.getHouseholdInformationsByStations(stations);

        if (householdInformations == null) {
            logger.error("station not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(householdInformations.toString());
        return ResponseEntity.ok(householdInformations);
    }

    @GetMapping(EndPointConstant.PERSON_INFO)
    public ResponseEntity<ResponsePersonInfoDTO> getInformationsByName(
            @RequestParam("lastName") String lastName) {
        ResponsePersonInfoDTO person = personInfoService.getPersonInfoByName(lastName);

        if (person == null) {
            logger.error("person not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(person.toString());
        return ResponseEntity.ok(person);
    }

    @GetMapping(EndPointConstant.EMAIL)
    public ResponseEntity<ResponseEmailDTO> getPersonsByCity(@RequestParam("city") String city) {
        ResponseEmailDTO emails = communityEmailService.getAllEmailsByCity(city);

        if (emails == null) {
            logger.error("city not found");
            return ResponseEntity.notFound().build();
        }

        logger.info(emails.toString());
        logger.debug("test");
        return ResponseEntity.ok(emails);
    }
}
