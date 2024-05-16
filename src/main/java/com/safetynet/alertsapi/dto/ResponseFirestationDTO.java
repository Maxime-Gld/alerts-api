package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;

import lombok.Data;

@Data
public class ResponseFirestationDTO {
    private int childCount;

    private int adultCount;

    private List<PersonResponseFirestationDTO> persons;
}
