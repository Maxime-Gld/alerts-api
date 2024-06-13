package com.safetynet.alertsapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FloodService {

	public static final Logger logger = LoggerFactory.getLogger(FloodService.class);

	private PersonService personService;
	private DtoMapper dtoMapper;

	public FloodService(PersonService personService, DtoMapper dtoMapper) {
		this.personService = personService;
		this.dtoMapper = dtoMapper;
	}

	public List<ResponseFloodDTO> getHouseholdInformationsByStations(List<Integer> stationNumbers) {

		logger.debug("Recherche des informations sur les menages couverts par les stations : " + stationNumbers);
		List<ResponseFloodDTO> householdInformationsList = new ArrayList<>();

		for (Integer stationNumber : stationNumbers) {
			List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber.toString());

			if (personsByStationNumber.isEmpty()) {
				logger.debug("numéro de staion : " + stationNumber + " introuvable");
			}

			Map<String, List<PersonResponseFloodDTO>> personsByAddress = dtoMapper
					.toMapPersonResponseFloodDTOList(personsByStationNumber);
			List<ResponseFloodDTO> householdInformations = dtoMapper.toResponseFloodDTOList(personsByAddress);
			householdInformationsList.addAll(householdInformations);
		}

		if (householdInformationsList.isEmpty()) {
			logger.debug("aucune informations trouvé pour les stations : " + stationNumbers);
			return null;
		}

		return householdInformationsList;
	}

}
