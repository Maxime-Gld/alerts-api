package com.safetynet.alertsapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.model.Firestation;

@Repository
public interface FirestationRepository {

    List<Firestation> findAll();

    Firestation findByStationNumber(String stationNumber);

    void save(Firestation firestation);

    void update(Firestation updatefirestation);

    void delete(Firestation firestation);

    Firestation findByAddress(String address);

}
