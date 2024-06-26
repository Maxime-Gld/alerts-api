package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;

public record PersonResponseFloodDTO (
    String firstName,
    String lastName,
    String phone,
    int age,
    MedicalRecordBaseDTO medicalRecord
) {}
