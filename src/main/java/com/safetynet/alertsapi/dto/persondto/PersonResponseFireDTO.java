package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;

public record PersonResponseFireDTO (
    String firstName,
    String lastName,
    String phone,
    int age,
    MedicalRecordBaseDTO medicalRecord
) {}



