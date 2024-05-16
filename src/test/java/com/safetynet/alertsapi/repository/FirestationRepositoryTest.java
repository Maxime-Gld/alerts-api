package com.safetynet.alertsapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.implementation.FirestationRepositoryImpl;

public class FirestationRepositoryTest {
    
    private FirestationRepository firestationRepository;
    
    @BeforeEach
    public void setUp() {
        List<Firestation> firestations = new ArrayList<>(Arrays.asList(
            new Firestation("123 Main St", "1"),
            new Firestation("456 Elm St", "2"),
            new Firestation("789 Oak St", "3")
        ));
        firestationRepository = new FirestationRepositoryImpl(firestations);
    }

    @Test
    public void testFindAll() {
        // Given (setUp)

        // When
        List<Firestation> firestations = firestationRepository.findAll();

        // Then
        assertNotNull(firestations);
        assertFalse(firestations.isEmpty()) ;      
    }

    @Test
    public void testFindByAddress() {
        // Given
        String address = "123 Main St";

        // When
        Firestation foundFirestation = firestationRepository.findByAddress(address);

        // Then
        assertNotNull(foundFirestation);
        assertEquals(address, foundFirestation.getAddress());
    }

    @Test
    public void testFindByAddressNotFound() {
        // Given
        String address = "999 Main St";

        // When
        Firestation foundFirestation = firestationRepository.findByAddress(address);

        // Then
        assertNull(foundFirestation);
    }

    @Test
    public void testFindByStationNumber() {
        // Given
        String stationNumber = "1";

        // When
        Firestation foundFirestation = firestationRepository.findByStationNumber(stationNumber);

        // Then
        assertNotNull(foundFirestation);
        assertEquals(stationNumber, foundFirestation.getStation());
    }

    @Test
    public void testFindByStationNumberNotFound() {
        // Given
        String stationNumber = "4";

        // When
        Firestation foundFirestation = firestationRepository.findByStationNumber(stationNumber);

        // Then
        assertNull(foundFirestation);
    }

    @Test
    public void testSave() {
        // Given
        Firestation newFirestation = new Firestation("999 Main St", "4");
        
        // When
        firestationRepository.save(newFirestation);
        
        List<Firestation> firestations = firestationRepository.findAll();
        // Then
        assertTrue(firestations.contains(newFirestation));
    }

    @Test
    public void testUpdate() {
        // Given
        List<Firestation> firestations = firestationRepository.findAll();
        Firestation firestationBeforeUpdate = firestations.get(0); // station 1
        Firestation updateFirestation = new Firestation("540 Main St", "1");

        // When
        firestationRepository.update(updateFirestation);

        // Then
        assertFalse(firestations.contains(firestationBeforeUpdate));
        assertTrue(firestations.contains(updateFirestation));
    }

    @Test
    public void testDelete() {
        // Given
        List<Firestation> firestations = firestationRepository.findAll();
        Firestation firestationToDelete = firestations.get(0); // station 1

        // When
        firestationRepository.delete(firestationToDelete);


        // Then
        assertFalse(firestations.contains(firestationToDelete));
    }
}
