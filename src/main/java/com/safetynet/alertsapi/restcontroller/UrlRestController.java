package com.safetynet.alertsapi.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.model.HouseholdInformations;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.service.PersonService;

@RestController
public class UrlRestController {

    @Autowired
    private PersonService personService;

    @GetMapping("/firestation")
    public ResponseFirestationDTO getPeopleByStationNumber(@RequestParam("stationNumber") String stationNumber) {
        ResponseFirestationDTO people = personService.getPersonsByStationNumber(stationNumber);
        return people;
    }

    @GetMapping("/childAlert")
    public Optional<ResponseChildAlertDTO> getChildsByAddress(@RequestParam("address") String address) {
        System.out.println("address : " + address);
        Optional<ResponseChildAlertDTO> childList = personService.getChildsByAddress(address);
        return childList;
    }

    @GetMapping("/phoneAlert")
    public ResponsePhoneAlertDTO getPhonesByStationNumber(@RequestParam("firestation") String stationNumber) {
        ResponsePhoneAlertDTO phones = personService.getPhonesByStationNumber(stationNumber);
        return phones;
    }

    @GetMapping("/fire")
    public ResponseFireDTO getPersonsByAdress(@RequestParam("address") String address) {
        ResponseFireDTO people = personService.getPersonsByAdress(address);
        return people;
    }

    @GetMapping("/flood")
    public ResponseEntity<?> getHouseholdInformationsByStations(@RequestParam("stations") List<Integer> stations) {
        List<HouseholdInformations> householdInformations = personService.getHouseholdInformationsByStations(stations);
        return ResponseEntity.ok(householdInformations);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<?> getInformationsByName(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        Person person = personService.getPersonInfoByName(firstName, lastName);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<?> getPersonsByCity(@RequestParam("city") String city) {
        List<String> emails = personService.getAllEmailsByCity(city);
        return ResponseEntity.ok(emails);
    }
}
