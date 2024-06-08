package com.safetynet.alertsapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FirestationService {

	private static final Logger logger = LogManager.getLogger(FirestationService.class);

    private PersonService personService;
	private DtoMapper dtoMapper;

    public FirestationService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
		this.dtoMapper = dtoMapper;
    }

    public ResponseFirestationDTO getPersonsByStationNumber(String stationNumber) {
		int child = 0;
		int adult = 0;

		logger.debug("Recherche des personnes par station numéro : " + stationNumber);
		List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber);

		if (personsByStationNumber.isEmpty()) {
			logger.debug("numéro de staion : " + stationNumber + " introuvable");
			return null;
		}

		logger.debug("décompte des enfants et des adultes.");
		for (Person person : personsByStationNumber) {
			if (personService.isAChild(person)) {
				child++;
			} else {
				adult++;
			}
		}
		logger.debug(child + " enfants et " + adult + " adultes trouvés");

		List<PersonResponseFirestationDTO> personsInformations = dtoMapper.toPersonResponseFirestationDTOList(personsByStationNumber);

		return new ResponseFirestationDTO(child, adult, personsInformations);
	}
}
