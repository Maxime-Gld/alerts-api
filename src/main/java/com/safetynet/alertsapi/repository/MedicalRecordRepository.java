package com.safetynet.alertsapi.repository;

import java.util.List;

import com.safetynet.alertsapi.model.MedicalRecord;

public interface MedicalRecordRepository {

    List<String> findAll();

    MedicalRecord findByFirstnameAndLastname(String firstname, String lastname);

    void save(MedicalRecord medicalRecord);

    void delete(MedicalRecord medicalRecord);
}
