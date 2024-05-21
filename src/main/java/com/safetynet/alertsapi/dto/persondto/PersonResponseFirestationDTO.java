package com.safetynet.alertsapi.dto.persondto;

public record PersonResponseFirestationDTO (
    String firstName,
    String lastName,
    String address,
    String phone
) {}
