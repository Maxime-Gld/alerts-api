package com.safetynet.alertsapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.interfaces.PersonInfo;
import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.model.HouseholdInformations;
import com.safetynet.alertsapi.model.MedicalRecord;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;
import com.safetynet.alertsapi.repository.PersonRepository;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private FirestationRepository firestationRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository,
            MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public ResponseFirestationDTO getPersonsByStationNumber(String stationNumber) {
        int child = 0;
        int adult = 0;

        Firestation firestation = firestationRepository.findByStationNumber(stationNumber);
        List<Person> personsByStationNumber = personRepository.findByAddress(firestation.getAddress());

        List<PersonResponseFirestationDTO> personsInformations = personsByStationNumber.stream().map(p -> {
            PersonResponseFirestationDTO personDTO = new PersonResponseFirestationDTO();
            personDTO.setFirstName(p.getFirstName());
            personDTO.setLastName(p.getLastName());
            personDTO.setAddress(p.getAddress());
            personDTO.setPhone(p.getPhone());
            return personDTO;
        }).collect(Collectors.toList());

        for (PersonResponseFirestationDTO person : personsInformations) {
            if (isAChild(person)) {
                child++;
            } else {
                adult++;
            }
        }

        ResponseFirestationDTO response = new ResponseFirestationDTO();
        response.setChildCount(child);
        response.setAdultCount(adult);
        response.setPersons(personsInformations);

        return response;
    }

    public List<Person> getChildsByAddress(String address) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChildsByAddress'");
    }

    public List<String> getPhonesByStationNumber(String stationNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhonesByStationNumber'");
    }

    public List<Person> getPersonsByAdress(String address) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersonsByAdress'");
    }

    public List<HouseholdInformations> getHouseholdInformationsByStations(List<Integer> stations) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHouseholdInformationsByStations'");
    }

    public List<String> getAllEmailsByCity(String city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmailsByCity'");
    }

    public Person getPersonInfoByName(String firstName, String lastName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersonInfoByName'");
    }

    private boolean isAChild(PersonInfo person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstnameAndLastname(firstName, lastName);
        if (medicalRecord != null) {
            String birthDate = medicalRecord.getBirthdate();
            LocalDate birthdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate now = LocalDate.now();
            int age = now.getYear() - birthdate.getYear();
            return age < 18;
        }
        return false;
    }

    public static void main(String[] args) {

        // tester la methode getPersonsByStationNumber
        
    }
}
