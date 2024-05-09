package com.safetynet.alertsapi.repository.implementation;

import java.io.File;
import java.util.List;

import com.safetynet.alertsapi.config.constant.FilePathConstant;
import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.utils.LoaderUtils;

public class FirestationRepositoryImpl implements FirestationRepository {

    private List<Firestation> firestations;

    public FirestationRepositoryImpl() {
        this.firestations = LoaderUtils
                .loadListFromFile(new File(FilePathConstant.JSON_FILE_PATH), Firestation.class, "firestations");
    }

    public FirestationRepositoryImpl(List<Firestation> firestationsList) {
        this.firestations = firestationsList;
    }

    @Override
    public List<Firestation> findAll() {
        return firestations;
    }

    @Override
    public void save(Firestation firestation) {
        firestations.add(firestation);
    }

    @Override
    public void update(Firestation updateFirestation) {
        Firestation firestation = findByStationNumber(updateFirestation.getStation()).get(0);
        if (firestation != null) {
            firestations.remove(firestation);
            firestations.add(updateFirestation);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public void delete(Firestation deleteFirestation) {
        Firestation firestation = findByStationNumber(deleteFirestation.getStation()).get(0);
        if (firestation != null) {
            firestations.remove(firestation);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public Firestation findByAddress(String address) {
        firestations = findAll();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }
        return null;
    }

    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        firestations = findAll();
        List<Firestation> firestationsByStationNumber = firestations.stream()
                .filter(f -> f.getStation().equals(stationNumber)).toList();
        if (firestationsByStationNumber.isEmpty()) {
            return null;
        }
        return firestationsByStationNumber;
    }
}
