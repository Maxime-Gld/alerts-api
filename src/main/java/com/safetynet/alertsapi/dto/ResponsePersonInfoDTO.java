package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonInfoDTO;

public record ResponsePersonInfoDTO(
    List<PersonInfoDTO> persons
) {}
