package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;

public record ResponseFirestationDTO (
    int childCount,
    int adultCount,
    List<PersonResponseFirestationDTO> persons
) {}
