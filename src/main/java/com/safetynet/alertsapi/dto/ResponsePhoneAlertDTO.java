package com.safetynet.alertsapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponsePhoneAlertDTO {
    private List<String> phoneNumbers;
}
