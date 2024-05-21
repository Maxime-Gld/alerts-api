package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.model.Firestation;

public record ResponseFireDTO (
    Firestation firestation,
    List<PersonResponseFireDTO> persons
) {}