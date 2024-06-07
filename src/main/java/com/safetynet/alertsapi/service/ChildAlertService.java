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

    public ChildAlertService(PersonService personService) {
        this.personService = personService;
    }
    
    public ResponseChildAlertDTO getChildsByAddress(String address) {
		// on récupère la liste des personnes par adresse
		List<Person> personsByAddress = personService.getPersonsListByAddress(address);

        if (personsByAddress.isEmpty()) {
            return null;
        }

		// on filtre pour séparer les enfants et les adultes
		List<Person> children = personService.getChildrenByAdress(personsByAddress);
        List<Person> adults = personService.getAdultsByAdress(personsByAddress);

		if (children.isEmpty()) {
            logger.info(address + " has no child");
			return new ResponseChildAlertDTO(new ArrayList<>(), new ArrayList<>());
		}
        
        // on transforme en DTO
		List<PersonResponseChildAlertDTO> childInformations = DtoMapper.toPersonResponseChildAlertDTOList(personsByAddress);
		List<PersonResponseChildAlertDTO> adultInformations = DtoMapper.toPersonResponseChildAlertDTOList(adults);

		return new ResponseChildAlertDTO(childInformations, adultInformations);
	}
}
