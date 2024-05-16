package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;

import lombok.Data;

@Data
public class ResponseChildAlertDTO {
    private List<PersonResponseChildAlertDTO> children;
    private List<PersonResponseChildAlertDTO> adults;
}
