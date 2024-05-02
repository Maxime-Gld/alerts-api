package com.safetynet.alertsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.service.FireService;

@Controller
@RequestMapping("/fire")
public class FireController {

    @Autowired
    private FireService fireService;

    @GetMapping
    public ResponseEntity<?> getPersonsByAdress(@RequestParam("address") String address) {
        List<Person> people = fireService.getPersonsByAdress(address);
        return ResponseEntity.ok(people);
    }
}
