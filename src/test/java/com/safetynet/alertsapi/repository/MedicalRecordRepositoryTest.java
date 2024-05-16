package com.safetynet.alertsapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.repository.implementation.MedicalRecordRepositoryImpl;

public class MedicalRecordRepositoryTest {
    
    private MedicalRecordRepository medicalRecordRepository;

    
    @BeforeEach
    void setUp() {
        List<MedicalRecord> medicalRecords = new ArrayList<>(Arrays.asList(
            new MedicalRecord("John", "Doe", "01/01/1970", new ArrayList<String>(), new ArrayList<String>()),
            new MedicalRecord("Jane", "Smith", "01/01/1975", new ArrayList<String>(), new ArrayList<String>()),
            new MedicalRecord("Bob", "Doe", "01/01/2000", new ArrayList<String>(), new ArrayList<String>()),
            new MedicalRecord("Alice", "Johnson", "01/01/1982", new ArrayList<String>(), new ArrayList<String>()),
            new MedicalRecord("Mark", "Jobert", "01/01/1990", new ArrayList<String>(), new ArrayList<String>())
        ));
        medicalRecordRepository = new MedicalRecordRepositoryImpl(medicalRecords);
    }

    @Test
    void testFindAll() {
        // Given (setUp)

        // When
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        // Then
        assertNotNull(medicalRecords);
        assertFalse(medicalRecords.isEmpty());
    }

    @Test
    void testFindByFirstnameAndLastname() {
        // Given
        String firstname = "John";
        String lastname = "Doe";

        // When
        MedicalRecord foundMedicalRecord = medicalRecordRepository.findByFirstnameAndLastname(firstname, lastname);

        // Then
        assertNotNull(foundMedicalRecord);
        assertEquals(firstname, foundMedicalRecord.getFirstName());
        assertEquals(lastname, foundMedicalRecord.getLastName());
    }

    @Test
    void testFindByFirstnameAndLastnameNotFound() {
        // Given (setUp)
        String firstname = "Jane";
        String lastname = "Doe";

        // When
        MedicalRecord notFoundMedicalRecord = medicalRecordRepository.findByFirstnameAndLastname(firstname, lastname);

        // Then
        assertNull(notFoundMedicalRecord);
    }

    @Test
    void testSave() {
        // Given
        MedicalRecord newMedicalRecord = new MedicalRecord("Max", "Test", "01/01/2000", new ArrayList<String>(),
                new ArrayList<String>());

        // When
        medicalRecordRepository.save(newMedicalRecord);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        // Then
        assertTrue(medicalRecords.contains(newMedicalRecord));
    }

    @Test
    void testUpdate() {
        // Given
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        MedicalRecord medicalRecordBeforeUpdate = medicalRecords.get(0); // John Doe
        MedicalRecord updateMedicalRecord = new MedicalRecord("John", "Doe", "05/05/2005", new ArrayList<String>(), new ArrayList<String>());

        // When
        medicalRecordRepository.update(updateMedicalRecord);

        // Then
        assertTrue(medicalRecords.contains(updateMedicalRecord));
        assertFalse(medicalRecords.contains(medicalRecordBeforeUpdate));
    }

    @Test
    void testDelete() {
        // Given
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        MedicalRecord medicalRecordToDelete = medicalRecords.get(0); // John Doe

        // When
        medicalRecordRepository.delete(medicalRecordToDelete);

        // Then
        assertFalse(medicalRecords.contains(medicalRecordToDelete));
    }
}
