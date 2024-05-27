package com.safetynet.alertsapi.dto;

import java.util.List;

import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;

public record ResponseFloodDTO (
    String address,
    List<PersonResponseFloodDTO> persons
) {}