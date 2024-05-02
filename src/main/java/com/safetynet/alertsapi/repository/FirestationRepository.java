package com.safetynet.alertsapi.repository;

import java.util.List;

import com.safetynet.alertsapi.model.Firestation;

public interface FirestationRepository {

    List<String> findAll();

    List<String> findByStationNumber(int stationNumber);

    void save(Firestation firestation);

    void delete(Firestation firestation);

    List<String> findByAddress(String address);
}
