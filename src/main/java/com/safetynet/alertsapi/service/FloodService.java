package com.safetynet.alertsapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FloodService {

    private PersonService personService;

    public FloodService(PersonService personService) {
        this.personService = personService;
    }

    	public List<ResponseFloodDTO> getHouseholdInformationsByStations(List<Integer> stationNumbers) {
		List<ResponseFloodDTO> householdInformationsList = new ArrayList<>();

		for (Integer stationNumber : stationNumbers) {
			List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber.toString());
			Map<String, List<PersonResponseFloodDTO>> personsByAddress = DtoMapper.toMapPersonResponseFloodDTOList(personsByStationNumber);
			List<ResponseFloodDTO> householdInformations = DtoMapper.toResponseFloodDTOList(personsByAddress);
            householdInformationsList.addAll(householdInformations);
		}
		
        if (householdInformationsList.isEmpty()) {
            return null;
        }

		return householdInformationsList;
	}
    
}
