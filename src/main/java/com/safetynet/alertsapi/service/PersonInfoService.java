package com.safetynet.alertsapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponsePersonInfoDTO;
import com.safetynet.alertsapi.dto.persondto.PersonInfoDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class PersonInfoService {

    public static final Logger logger = LogManager.getLogger(PersonInfoService.class);

    private PersonService personService;
    private DtoMapper dtoMapper;

    public PersonInfoService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    public ResponsePersonInfoDTO getPersonInfoByName(String lastName) {

        logger.debug("Recherche de personnes pour le nom : " + lastName);
        List<Person> persons = personService.findByLastname(lastName);

        if (persons == null) {
            logger.debug("nom : " + lastName + " introuvable ou erroné");
            return null;
        }

        List<PersonInfoDTO> personsInformations = dtoMapper.toPersonInfoDTOList(persons);

        logger.debug("Personnes trouvées : " + personsInformations.size());
        return new ResponsePersonInfoDTO(personsInformations);
    }
}
