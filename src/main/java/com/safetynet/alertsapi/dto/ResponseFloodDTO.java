package com.safetynet.alertsapi.dto;

import com.safetynet.alertsapi.dto.household.HouseholdInformationsDTO;
import com.safetynet.alertsapi.model.Firestation;

public record ResponseFloodDTO (
    Firestation firestation,
    HouseholdInformationsDTO householdInformations
) {}
