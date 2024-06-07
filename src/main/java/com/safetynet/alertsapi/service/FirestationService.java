package com.safetynet.alertsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FirestationService {

    private PersonService personService;

    public FirestationService(PersonService personService) {
        this.personService = personService;
    }

    public ResponseFirestationDTO getPersonsByStationNumber(String stationNumber) {
		int child = 0;
		int adult = 0;

		List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber);

		if (personsByStationNumber.isEmpty()) {
			return null;
		}

		for (Person person : personsByStationNumber) {
			if (personService.isAChild(person)) {
				child++;
			} else {
				adult++;
			}
		}

		List<PersonResponseFirestationDTO> personsInformations = DtoMapper.toPersonResponseFirestationDTOList(personsByStationNumber);

		return new ResponseFirestationDTO(child, adult, personsInformations);
	}
}
