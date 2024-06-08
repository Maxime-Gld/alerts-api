package com.safetynet.alertsapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseEmailDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class CommunityEmailService {

    private static final Logger logger = LogManager.getLogger(CommunityEmailService.class);

    private PersonService personService;
    private DtoMapper dtoMapper;

    public CommunityEmailService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    public ResponseEmailDTO getAllEmailsByCity(String city) {

        logger.debug("Recherche des adresses email dans la ville : " + city);
        List<Person> personsByCity = personService.getPersonsByCity(city);

        if (personsByCity == null) {
            logger.debug("aucune personne dans la ville : " + city + " ou ville erronée");
            return null;
        }

        logger.debug("Adresses email trouvées : " + personsByCity.size());
        return dtoMapper.toResponseEmailDTO(personsByCity);
    }
}
