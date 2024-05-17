package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordResponseFloodDTO;

import lombok.Data;

@Data
public class PersonResponseFloodDTO {
    
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecordResponseFloodDTO medicalRecord;
}
