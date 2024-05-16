package com.safetynet.alertsapi.dto.persondto;

import com.safetynet.alertsapi.interfaces.PersonInfo;

import lombok.Data;

@Data
public class PersonResponseFirestationDTO implements PersonInfo {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

}
