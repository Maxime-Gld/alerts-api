package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.interfaces.PersonName;

import lombok.Data;

@Data
public class PersonResponseChildAlertDTO implements PersonName {
    private String firstName;
    private String lastName;
    private int age;
}
