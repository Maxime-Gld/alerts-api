package com.safetynet.alertsapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.model.MedicalRecord;

@Repository
public interface MedicalRecordRepository {

    List<MedicalRecord> findAll();

    MedicalRecord findByFirstnameAndLastname(String firstname, String lastname);

    void save(MedicalRecord medicalRecord);

    void update(MedicalRecord updateMedicalrecord);

    void delete(MedicalRecord medicalRecord);

}
