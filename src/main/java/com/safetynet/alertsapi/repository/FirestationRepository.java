package com.safetynet.alertsapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.model.Firestation;

@Repository
public interface FirestationRepository {

    List<Firestation> findAll();

    List<Firestation> findByStationNumber(String stationNumber);

    Firestation save(Firestation firestation);

    Firestation update(Firestation updatefirestation);

    void delete(Firestation firestation);

    Firestation findByAddress(String address);

    Firestation findByFirestation(Firestation firestation);

}
