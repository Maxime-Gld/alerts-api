package com.safetynet.alertsapi.dto;

import java.util.List;

public record ResponseEmailDTO(
    List<String> emails
) {}
