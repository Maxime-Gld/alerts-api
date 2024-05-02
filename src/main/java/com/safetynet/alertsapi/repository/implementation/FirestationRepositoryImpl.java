package com.safetynet.alertsapi.repository.implementation;

import java.util.List;

import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;

public class FirestationRepositoryImpl implements FirestationRepository {

    @Override
    public List<String> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findByStationNumber(int stationNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Firestation firestation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Firestation firestation) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<String> findByAddress(String address) {
        // TODO Auto-generated method stub
        return null;
    }
}
