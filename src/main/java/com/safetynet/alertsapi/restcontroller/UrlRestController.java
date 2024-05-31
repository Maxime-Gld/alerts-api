package com.safetynet.alertsapi.restcontroller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.safetynet.alertsapi.service.PersonService;

@RestController
public class UrlRestController {

    private static final Logger logger = LogManager.getLogger(UrlRestController.class);

    @Autowired
    private PersonService personService;

    @GetMapping("/firestation")
    public ResponseFirestationDTO getPeopleByStationNumber(@RequestParam("stationNumber") String stationNumber) {
        ResponseFirestationDTO people = personService.getPersonsByStationNumber(stationNumber);
        logger.info(people);
        return people;
    }

    @GetMapping("/childAlert")
    public Optional<ResponseChildAlertDTO> getChildsByAddress(@RequestParam("address") String address) {
        Optional<ResponseChildAlertDTO> childList = personService.getChildsByAddress(address);
        logger.info(childList);
        return childList;
    }

    @GetMapping("/phoneAlert")
    public ResponsePhoneAlertDTO getPhonesByStationNumber(@RequestParam("firestation") String stationNumber) {
        ResponsePhoneAlertDTO phones = personService.getPhonesByStationNumber(stationNumber);
        logger.info(phones);
        return phones;
    }

    @GetMapping("/fire")
    public ResponseFireDTO getPersonsByAdress(@RequestParam("address") String address) {
        ResponseFireDTO people = personService.getPersonsByAdress(address);
        logger.info(people);
        return people;
    }

    @GetMapping("/flood/stations")
    public List<ResponseFloodDTO> getHouseholdInformationsByStations(@RequestParam("stations") List<Integer> stations) {
        List<ResponseFloodDTO> householdInformations = personService.getHouseholdInformationsByStations(stations);
        logger.info(householdInformations);
        return householdInformations;
    }

    @GetMapping("/personInfo")
    public ResponsePersonInfoDTO getInformationsByName(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        ResponsePersonInfoDTO person = personService.getPersonInfoByName(firstName, lastName);
        logger.info(person);
        return person;
    }

    @GetMapping("/communityEmail")
    public ResponseEmailDTO getPersonsByCity(@RequestParam("city") String city) {
        ResponseEmailDTO emails = personService.getAllEmailsByCity(city);
        logger.info(emails);
        return emails;
    }
}
