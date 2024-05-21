package com.safetynet.alertsapi.dto.persondto;

import java.util.List;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;

public record PersonInfoDTO (
    String firstName,
    String lastName,
    String address,
    int age,
    String phone,
    List<MedicalRecordBaseDTO> medicalRecord
) {}
