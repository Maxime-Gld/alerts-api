package com.safetynet.alertsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alertsapi.service.PhoneAlertService;

@Controller
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping
    public ResponseEntity<?> getPhonesByStationNumber(@RequestParam("station_number") String stationNumber) {
        List<String> phones = phoneAlertService.getPhonesByStationNumber(stationNumber);
        return ResponseEntity.ok(phones);
    }
}
