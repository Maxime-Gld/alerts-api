package com.safetynet.alertsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponsePersonInfoDTO;
import com.safetynet.alertsapi.dto.persondto.PersonInfoDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class PersonInfoService {

    private PersonService personService;

    public PersonInfoService(PersonService personService) {
        this.personService = personService;
    }

    	public ResponsePersonInfoDTO getPersonInfoByName(String lastName) {

		List<Person> persons = personService.findByLastname(lastName);

		if (persons == null) {
			return null;
		}

        List<PersonInfoDTO> personsInformations = DtoMapper.toPersonInfoDTOList(persons);

        return new ResponsePersonInfoDTO(personsInformations);
    }
}
