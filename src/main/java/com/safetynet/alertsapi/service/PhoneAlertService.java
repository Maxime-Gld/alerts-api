package com.safetynet.alertsapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class PhoneAlertService {

    public static final Logger logger = LogManager.getLogger(PhoneAlertService.class);

    private PersonService personService;
    private DtoMapper dtoMapper;

    public PhoneAlertService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    public ResponsePhoneAlertDTO getPhonesByStationNumber(String stationNumber) {

        logger.debug("Recherche des numéros de téléphone par station numéro : " + stationNumber);
        List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber);

        if (personsByStationNumber.isEmpty()) {
            logger.debug("numéro de staion : " + stationNumber + " introuvable ou erroné");
            return null;
        }

        logger.debug("numérios de numéro trouvés : " + personsByStationNumber.size());
        return dtoMapper.toResponsePhoneAlertDTO(personsByStationNumber);
    }
}
