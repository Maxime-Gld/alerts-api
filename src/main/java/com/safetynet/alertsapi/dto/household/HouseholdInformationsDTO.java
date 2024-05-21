package com.safetynet.alertsapi.dto.household;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;

public record HouseholdInformationsDTO (
    String address,
    List<PersonResponseFloodDTO> persons
) {}
    

