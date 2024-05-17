package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.household.HouseholdInformationsDTO;
import com.safetynet.alertsapi.model.Firestation;

import lombok.Data;

@Data
public class ResponseFloodDTO {
    private Firestation firestation;
    private List<HouseholdInformationsDTO> householdInformations;
}
