package com.safetynet.alertsapi.model;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecord {
    
    private String firstname;

    private String lastname;

    private String birthdate;

    private List<String> medications;

    private List<String> allergies;

}