package com.safetynet.alertsapi.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.utils.DtoMapper;

@Service
public class PhoneAlertService {

    public static final Logger logger = LoggerFactory.getLogger(PhoneAlertService.class);

    private PersonService personService;
    private DtoMapper dtoMapper;

    public PhoneAlertService(PersonService personService, DtoMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Récupère les numéros de contact pour l'URL GET /phoneAlert
     * 
     * @param stationNumber numéro de la station
     * @return une instance de ResponsePhoneAlertDTO contenant les numéros de
     *         contact
     */
    public ResponsePhoneAlertDTO getPhonesByStationNumber(String stationNumber) {

        logger.debug("Recherche des numéros de téléphone par numéro de station : " + stationNumber);
        List<Person> personsByStationNumber = personService.getPersonsListByStationNumber(stationNumber);

        if (personsByStationNumber.isEmpty()) {
            logger.debug("numéro de station : " + stationNumber + " introuvable ou erroné");
            return null;
        }

        logger.debug("numéro(s) de téléphone trouvés : " + personsByStationNumber.size());
        return dtoMapper.toResponsePhoneAlertDTO(personsByStationNumber);
    }
}
