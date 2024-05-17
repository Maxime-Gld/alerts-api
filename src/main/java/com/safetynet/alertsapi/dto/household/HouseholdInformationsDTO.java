package com.safetynet.alertsapi.dto.household;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;

import lombok.Data;

@Data
public class HouseholdInformationsDTO {
    
    private String address;
    private List<PersonResponseFloodDTO> persons;
}
