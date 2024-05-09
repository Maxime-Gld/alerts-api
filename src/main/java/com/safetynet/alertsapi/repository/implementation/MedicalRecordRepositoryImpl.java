package com.safetynet.alertsapi.repository.implementation;

import java.io.File;
import java.util.List;

import com.safetynet.alertsapi.config.constant.FilePathConstant;
import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;
import com.safetynet.alertsapi.utils.LoaderUtils;

public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private List<MedicalRecord> medicalRecords;

    public MedicalRecordRepositoryImpl() {
        this.medicalRecords = LoaderUtils
                .loadListFromFile(new File(FilePathConstant.JSON_FILE_PATH), MedicalRecord.class, "medicalrecords");
    }

    public MedicalRecordRepositoryImpl(List<MedicalRecord> medicalRecordsList) {
        this.medicalRecords = medicalRecordsList;
    }

    @Override
    public List<MedicalRecord> findAll() {
        return medicalRecords;
    }

    @Override
    public MedicalRecord findByFirstnameAndLastname(String firstname, String lastname) {

        medicalRecords = findAll();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstname) && medicalRecord.getLastName().equals(lastname)) {
                return medicalRecord;
            }
        }
        return null;
    }

    @Override
    public void save(MedicalRecord newMedicalRecord) {
        medicalRecords.add(newMedicalRecord);
        // que faire en cas d'erreur ?
    }

    @Override
    public void update(MedicalRecord updateMedicalrecord) {
        MedicalRecord medicalrecord = findByFirstnameAndLastname(updateMedicalrecord.getFirstName(),
                updateMedicalrecord.getLastName());
        if (medicalrecord != null) {
            medicalRecords.remove(medicalrecord);
            medicalRecords.add(updateMedicalrecord);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public void delete(MedicalRecord deleteMedicalRecord) {
        MedicalRecord medicalRecord = findByFirstnameAndLastname(deleteMedicalRecord.getFirstName(),
                deleteMedicalRecord.getLastName());
        if (medicalRecord != null) {
            medicalRecords.remove(medicalRecord);
        }
        // que faire en cas d'erreur ?
    }
}
