package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordResponseFireDTO;
import com.safetynet.alertsapi.interfaces.PersonInfo;

import lombok.Data;

@Data
public class PersonResponseFireDTO implements PersonInfo {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecordResponseFireDTO medicalRecord;

}
