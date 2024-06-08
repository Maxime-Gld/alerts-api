package com.safetynet.alertsapi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class ChildAlertService {

    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);
    
    private PersonService personService;
    private DtoMapper dtoMapper;

    public ChildAlertService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }
    
    public ResponseChildAlertDTO getChildsByAddress(String address) {

        logger.debug("Recherche des enfants par adresse : " + address);
		List<Person> personsByAddress = personService.getPersonsListByAddress(address);

        if (personsByAddress == null) {
            logger.debug("aucune personne dans l'adresse : " + address + " ou adresse erronée");
            return null;
        }

		List<Person> children = personService.getChildrenByAdress(personsByAddress);
        List<Person> adults = personService.getAdultsByAdress(personsByAddress);

		if (children.isEmpty()) {
            logger.info(address + " has no child");
			return new ResponseChildAlertDTO(new ArrayList<>(), new ArrayList<>());
		}
        
        logger.debug("Enfants trouvés : " + children.size());
		List<PersonResponseChildAlertDTO> childInformations = dtoMapper.toPersonResponseChildAlertDTOList(personsByAddress);
		List<PersonResponseChildAlertDTO> adultInformations = dtoMapper.toPersonResponseChildAlertDTOList(adults);

		return new ResponseChildAlertDTO(childInformations, adultInformations);
	}
}
