package com.safetynet.alertsapi.repository.implementation;

import java.util.List;

import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;

public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    @Override
    public List<String> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MedicalRecord findByFirstnameAndLastname(String firstname, String lastname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(MedicalRecord medicalRecord) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        // TODO Auto-generated method stub
    }
}
