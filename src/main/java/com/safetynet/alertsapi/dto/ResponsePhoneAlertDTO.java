package com.safetynet.alertsapi.dto;

import java.util.List;

public record ResponsePhoneAlertDTO (
    List<String> phoneNumbers
) {}

