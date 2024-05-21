package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;

public record PersonInfoDTO (
    String firstName,
    String lastName,
    String address,
    int age,
    String email,
    MedicalRecordBaseDTO medicalRecord
) {}
