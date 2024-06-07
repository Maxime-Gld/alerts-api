package com.safetynet.alertsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FireService {
    
    private PersonService personService;

    public FireService(PersonService personService) {
        this.personService = personService;
    }

    public ResponseFireDTO getPersonsByAdress(String address) {

		Firestation firestation = personService.getFirestationByAddress(address);

        if (firestation == null) {
            return null;
        }

		List<Person> personsByAddress = personService.getPersonsListByAddress(address);
		List<PersonResponseFireDTO> personsInformations = DtoMapper.toPersonResponseFireDTOList(personsByAddress);

		return new ResponseFireDTO(firestation, personsInformations);
	}
}
