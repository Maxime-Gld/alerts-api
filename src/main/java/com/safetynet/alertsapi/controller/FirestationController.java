package com.safetynet.alertsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.service.FirestationService;

@Controller
@RequestMapping("/firestation")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping
    public ResponseEntity<?> getPeopleByStationNumber(@RequestParam("station_number") String stationNumber) {
        List<Person> people = firestationService.getPersonsByStationNumber(stationNumber);
        return ResponseEntity.ok(people);
    }
}
