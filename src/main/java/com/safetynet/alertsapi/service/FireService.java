package com.safetynet.alertsapi.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class FireService {

    public static final Logger logger = LoggerFactory.getLogger(FireService.class);
    
    private PersonService personService;
    private DtoMapper dtoMapper;

    public FireService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    public ResponseFireDTO getPersonsByAdress(String address) {

        logger.debug("Recherche des personnes par adresse : " + address);
		Firestation firestation = personService.getFirestationByAddress(address);

        if (firestation == null) {
            logger.debug("adresse : " + address + " introuvable ou erronée");
            return null;
        }

		List<Person> personsByAddress = personService.getPersonsListByAddress(address);
		List<PersonResponseFireDTO> personsInformations = dtoMapper.toPersonResponseFireDTOList(personsByAddress);

        logger.debug("Personnes trouvées : " + personsByAddress.size());
		return new ResponseFireDTO(firestation, personsInformations);
	}
}
