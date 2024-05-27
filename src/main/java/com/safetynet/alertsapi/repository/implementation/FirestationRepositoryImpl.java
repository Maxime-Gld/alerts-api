package com.safetynet.alertsapi.repository.implementation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.config.constant.FilePathConstant;
import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.utils.LoaderUtils;

@Repository
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
    public Firestation save(Firestation firestation) {
        firestations.add(firestation);
        return firestation;
    }

    @Override
    public Firestation update(Firestation updateFirestation) {
        Firestation firestation = findByFirestation(updateFirestation);
        if (firestation != null) {
            firestations.remove(firestation);
            firestations.add(updateFirestation);
        }

        return updateFirestation;
        // que faire en cas d'erreur ?
    }

    @Override
    public Firestation findByFirestation(Firestation firestation) {
        if (firestations.contains(firestation)) {
            return firestation;
        }
        return null;
    }

    @Override
    public void delete(Firestation deleteFirestation) {
        List<Firestation> firestationByStationNumber = findByStationNumber(deleteFirestation.getStation());
        if (!firestationByStationNumber.isEmpty()) {
            firestationByStationNumber.remove(deleteFirestation);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public Firestation findByAddress(String address) {
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }
        return null;
    }

    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        
        List<Firestation> firestationsByStationNumber = new ArrayList<>();

        for (Firestation firestation : firestations) {
            if (firestation.getStation().equals(stationNumber)) {
                firestationsByStationNumber.add(firestation);
            }
            
        }

        return firestationsByStationNumber;
    }
}
