package com.safetynet.alertsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.service.ChildAlertService;

@Controller
@RequestMapping("/childAlert")
public class ChildAlertController {

    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping
    public ResponseEntity<?> getChildsByAddress(@RequestParam("address") String address) {
        List<Person> childList = childAlertService.getChildsByAddress(address);
        return ResponseEntity.ok(childList);
    }
}
