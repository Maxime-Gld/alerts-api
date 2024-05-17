package com.safetynet.alertsapi.dto.medicalrecorddto;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecordResponseFloodDTO {
    
    private List<String> medications;

    private List<String> allergies;
}
