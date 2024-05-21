package com.safetynet.alertsapi.dto.persondto;

public record PersonResponseChildAlertDTO (
    String firstName,
    String lastName,
    int age
) {}