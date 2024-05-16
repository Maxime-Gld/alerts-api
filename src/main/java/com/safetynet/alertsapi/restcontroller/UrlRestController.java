package com.safetynet.alertsapi.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
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
    public ResponseEntity<?> getChildsByAddress(@RequestParam("address") String address) {
        List<Person> childList = personService.getChildsByAddress(address);
        return ResponseEntity.ok(childList);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhonesByStationNumber(@RequestParam("firestation") String stationNumber) {
        List<String> phones = personService.getPhonesByStationNumber(stationNumber);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/fire")
    public ResponseEntity<?> getPersonsByAdress(@RequestParam("address") String address) {
        List<Person> people = personService.getPersonsByAdress(address);
        return ResponseEntity.ok(people);
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
