package com.safetynet.alertsapi.dto.medicalrecorddto;

import java.util.List;

public record MedicalRecordBaseDTO (
    List<String> medications,
    List<String> allergies
) {}


