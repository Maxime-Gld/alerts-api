package com.safetynet.alertsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alertsapi.model.HouseholdInformations;
import com.safetynet.alertsapi.service.FloodService;

@Controller
@RequestMapping("/flood")
public class FloodController {

    @Autowired
    private FloodService floodService;

    @GetMapping
    public ResponseEntity<?> getHouseholdInformationsByStations(@RequestParam("stations") List<Integer> stations) {
        List<HouseholdInformations> householdInformations = floodService.getHouseholdInformationsByStations(stations);
        return ResponseEntity.ok(householdInformations);
    }
}
