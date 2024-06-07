package com.safetynet.alertsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseEmailDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class CommunityEmailService {

    private PersonService personService;

    public CommunityEmailService(PersonService personService) {
        this.personService = personService;
    }

    public ResponseEmailDTO getAllEmailsByCity(String city) {
        List<Person> personsByCity = personService.getPersonsByCity(city);

        if (personsByCity == null) {
            return null;
        }

        return DtoMapper.toResponseEmailDTO(personsByCity);
    }
}
