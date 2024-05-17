package com.safetynet.alertsapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.dto.household.HouseholdInformationsDTO;
import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordResponseFireDTO;
import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordResponseFloodDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;
import com.safetynet.alertsapi.interfaces.PersonName;
import com.safetynet.alertsapi.model.Firestation;
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

    public Optional<ResponseChildAlertDTO> getChildsByAddress(String address) {
        // on récupère la liste des personnes par adresse
        List<Person> personsByAddress = personRepository.findByAddress(address);

        // pour chaque personne trouvé, on récupère son nom, prénom et on calcul son age
        List<PersonResponseChildAlertDTO> personsInformations = personsByAddress.stream().map(p -> {
            PersonResponseChildAlertDTO personDTO = new PersonResponseChildAlertDTO();
            personDTO.setFirstName(p.getFirstName());
            personDTO.setLastName(p.getLastName());
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstnameAndLastname(p.getFirstName(), p.getLastName());
            personDTO.setAge(getAge(medicalRecord));
            return personDTO;
        }).collect(Collectors.toList());
        
        // on filtre pour séparer les enfants et les adultes
        List<PersonResponseChildAlertDTO> children = personsInformations.stream().filter(p -> isAChild(p)).toList();
        List<PersonResponseChildAlertDTO> adults = personsInformations.stream().filter(p -> !isAChild(p)).toList();
        
        if (children.isEmpty()) {
            return Optional.empty();
        }

        ResponseChildAlertDTO response = new ResponseChildAlertDTO();
        response.setChildren(children);
        response.setAdults(adults);

        return Optional.of(response);
    }

    public ResponsePhoneAlertDTO getPhonesByStationNumber(String stationNumber) {
        Firestation firestation = firestationRepository.findByStationNumber(stationNumber);
        List<Person> personsByStationNumber = personRepository.findByAddress(firestation.getAddress());

        List<String> phoneNumbers = personsByStationNumber.stream().map(p -> p.getPhone()).collect(Collectors.toList());

        ResponsePhoneAlertDTO response = new ResponsePhoneAlertDTO();
        response.setPhoneNumbers(phoneNumbers);

        return response;
    }

    public ResponseFireDTO getPersonsByAdress(String address) {
        
        Firestation firestation = firestationRepository.findByAddress(address);
        List<Person> personsByAddress = personRepository.findByAddress(address);

        List<PersonResponseFireDTO> personsInformations = personsByAddress.stream().map(p -> {
            // on récupere le dossier medicale puis on l'injecte dans le medicalRecordDTO
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstnameAndLastname(p.getFirstName(), p.getLastName());
            MedicalRecordResponseFireDTO medicalRecordDTO = new MedicalRecordResponseFireDTO();
            medicalRecordDTO.setAllergies(medicalRecord.getAllergies());
            medicalRecordDTO.setMedications(medicalRecord.getMedications());

            // on construit PersonResponseFireDTO puis on injecte son medicalRecordDTO
            PersonResponseFireDTO personDTO = new PersonResponseFireDTO();
            personDTO.setFirstName(p.getFirstName());
            personDTO.setLastName(p.getLastName());
            personDTO.setPhone(p.getPhone());
            personDTO.setAge(getAge(medicalRecord));
            personDTO.setMedicalRecord(medicalRecordDTO);
            return personDTO;
        }).collect(Collectors.toList());

        ResponseFireDTO response = new ResponseFireDTO();
        response.setFirestation(firestation);
        response.setPersons(personsInformations);

        return response;
    }

    public List<ResponseFloodDTO> getHouseholdInformationsByStations(List<Integer> stations) {

        List<ResponseFloodDTO> responseList = new ArrayList<>();
        
        
        for (Integer stationNumber : stations) {
            Firestation firestation = firestationRepository.findByStationNumber(stationNumber.toString());
            
            if (firestation == null) {
                throw new IllegalArgumentException("Station number not found");
            }
            // on récupère la liste des personnes par adresse
            List<Person> personsByStationNumber = personRepository.findByAddress(firestation.getAddress());
            
            // pour chaque personne, on récupère son nom, prénom et on calcul son age
            List<PersonResponseFloodDTO> personsInformations = personsByStationNumber.stream().map(p -> {
                // dossier medical pour la personne
                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstnameAndLastname(p.getFirstName(), p.getLastName());
                MedicalRecordResponseFloodDTO medicalRecordDTO = new MedicalRecordResponseFloodDTO();
                medicalRecordDTO.setAllergies(medicalRecord.getAllergies());
                medicalRecordDTO.setMedications(medicalRecord.getMedications());
                
                // la personne
                PersonResponseFloodDTO personDTO = new PersonResponseFloodDTO();
                personDTO.setFirstName(p.getFirstName());
                personDTO.setLastName(p.getLastName());
                personDTO.setPhone(p.getPhone());
                personDTO.setAge(getAge(medicalRecord));
                personDTO.setMedicalRecord(medicalRecordDTO);
                return personDTO;
            }).collect(Collectors.toList());
            
            List<HouseholdInformationsDTO> householdInformations = personsInformations.stream().map(p -> {
                HouseholdInformationsDTO householdInformationsDTO = new HouseholdInformationsDTO();
                householdInformationsDTO.setAddress(firestation.getAddress());
                householdInformationsDTO.setPersons(personsInformations);
                return householdInformationsDTO;
            }).collect(Collectors.toList());
            
            
            ResponseFloodDTO response = new ResponseFloodDTO();
            response.setHouseholdInformations(householdInformations);
            response.setFirestation(firestation);

            responseList.add(response);
        }

        return responseList;
    }

    public List<String> getAllEmailsByCity(String city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmailsByCity'");
    }

    public Person getPersonInfoByName(String firstName, String lastName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersonInfoByName'");
    }

    private boolean isAChild(PersonName person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstnameAndLastname(firstName, lastName);
        if (medicalRecord != null) {
            int age = getAge(medicalRecord);
            return age < 18;
        }
        return false;
    }

    private int getAge(MedicalRecord medicalRecord) {
        String birthDate = medicalRecord.getBirthdate();
        LocalDate birthdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate now = LocalDate.now();
        return now.getYear() - birthdate.getYear();
    }

    public static void main(String[] args) {

        // tester la methode getPersonsByStationNumber

    }
}
