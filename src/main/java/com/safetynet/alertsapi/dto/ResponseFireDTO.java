package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.model.Firestation;

import lombok.Data;

@Data
public class ResponseFireDTO {
    
    private Firestation firestation;
    List<PersonResponseFireDTO> persons;
}
