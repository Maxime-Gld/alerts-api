package com.safetynet.alertsapi.model;

import java.util.List;

import lombok.Data;

@Data
public class Person {

    private String firstname;

    private String lastname;

    private String address;

    private String city;

    private String zip;

    private String phone;

    private String email;

    private List<String> medications;

    private List<String> allergies;
}
