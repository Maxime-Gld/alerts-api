package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.interfaces.PersonName;

import lombok.Data;

@Data
public class PersonResponseFirestationDTO implements PersonName {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

}
