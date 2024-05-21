package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;

public record ResponseChildAlertDTO (
    List<PersonResponseChildAlertDTO> children,
    List<PersonResponseChildAlertDTO> adults
) {}
