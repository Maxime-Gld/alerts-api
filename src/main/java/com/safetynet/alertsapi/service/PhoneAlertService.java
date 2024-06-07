package com.safetynet.alertsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class PhoneAlertService {

    private PersonService personService;

    public PhoneAlertService(PersonService personService) {
        this.personService = personService;
    }

    public ResponsePhoneAlertDTO getPhonesByStationNumber(String stationNumber) {
        List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber);
        return DtoMapper.toResponsePhoneAlertDTO(personsByStationNumber);
    }
}
