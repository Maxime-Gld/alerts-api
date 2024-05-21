package com.safetynet.alertsapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.dto.household.HouseholdInformationsDTO;
import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;
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

        List<PersonResponseFirestationDTO> personsInformations = personsByStationNumber.stream()
                .map(p -> new PersonResponseFirestationDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()))
                .toList();

        for (Person person : personsByStationNumber) {
            if (isAChild(person)) {
                child++;
            } else {
                adult++;
            }
        }

        return new ResponseFirestationDTO(child, adult, personsInformations);
    }

    public Optional<ResponseChildAlertDTO> getChildsByAddress(String address) {
        // on récupère la liste des personnes par adresse
        List<Person> personsByAddress = personRepository.findByAddress(address);

        // on filtre pour séparer les enfants et les adultes
        List<Person> children = personsByAddress.stream().filter(p -> isAChild(p)).toList();
        List<Person> adults = personsByAddress.stream().filter(p -> !isAChild(p)).toList();

        // pour chaque personne trouvé, on récupère son nom, prénom et on calcul son
        // age
        List<PersonResponseChildAlertDTO> childInformations = children.stream()
                .map(c -> new PersonResponseChildAlertDTO(
                        c.getFirstName(),
                        c.getLastName(),
                        getAge(medicalRecordRepository.findByFirstnameAndLastname(
                                c.getFirstName(),
                                c.getLastName()))))
                .toList();

        List<PersonResponseChildAlertDTO> adultInformations = adults.stream()
                .map(a -> new PersonResponseChildAlertDTO(
                        a.getFirstName(),
                        a.getLastName(),
                        getAge(medicalRecordRepository.findByFirstnameAndLastname(
                                a.getFirstName(),
                                a.getLastName()))))
                .toList();

        if (children.isEmpty()) {
            return Optional.of(new ResponseChildAlertDTO(new ArrayList<>(), new ArrayList<>()));
        }

        return Optional.of(new ResponseChildAlertDTO(childInformations, adultInformations));
    }

    public ResponsePhoneAlertDTO getPhonesByStationNumber(String stationNumber) {
        Firestation firestation = firestationRepository.findByStationNumber(stationNumber);
        List<Person> personsByStationNumber = personRepository.findByAddress(firestation.getAddress());

        List<String> phoneNumbers = personsByStationNumber.stream().map(p -> p.getPhone()).toList();

        return new ResponsePhoneAlertDTO(phoneNumbers);
    }

    public ResponseFireDTO getPersonsByAdress(String address) {

        Firestation firestation = firestationRepository.findByAddress(address);
        List<Person> personsByAddress = personRepository.findByAddress(address);

        List<PersonResponseFireDTO> personsInformations = personsByAddress.stream()
                .map(p -> new PersonResponseFireDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getPhone(),
                        getAge(medicalRecordRepository.findByFirstnameAndLastname(
                                p.getFirstName(),
                                p.getLastName())),
                        new MedicalRecordBaseDTO(
                                medicalRecordRepository.findByFirstnameAndLastname(
                                        p.getFirstName(),
                                        p.getLastName()).getMedications(),
                                medicalRecordRepository.findByFirstnameAndLastname(
                                        p.getFirstName(),
                                        p.getLastName()).getAllergies())))
                .toList();

        return new ResponseFireDTO(firestation, personsInformations);
    }

    public List<ResponseFloodDTO> getHouseholdInformationsByStations(List<Integer> stations) {
        List<ResponseFloodDTO> responseFloodDTOList = new ArrayList<>();

        for (Integer station : stations) {
            Firestation firestation = firestationRepository.findByStationNumber(station.toString());
            List<Person> personsByStationNumber = personRepository.findByAddress(firestation.getAddress());

            List<PersonResponseFloodDTO> personsInformations = personsByStationNumber.stream()
                    .map(p -> new PersonResponseFloodDTO(p.getFirstName(),
                            p.getLastName(),
                            p.getPhone(),
                            getAge(medicalRecordRepository.findByFirstnameAndLastname(
                                    p.getFirstName(),
                                    p.getLastName())),
                            new MedicalRecordBaseDTO(
                                    medicalRecordRepository.findByFirstnameAndLastname(
                                            p.getFirstName(),
                                            p.getLastName()).getMedications(),
                                    medicalRecordRepository.findByFirstnameAndLastname(
                                            p.getFirstName(),
                                            p.getLastName()).getAllergies())))
                    .toList();

            HouseholdInformationsDTO householdInformations = new HouseholdInformationsDTO(
                    firestation.getAddress(),
                    personsInformations);

            responseFloodDTOList.add(new ResponseFloodDTO(firestation, householdInformations));
        }

        return responseFloodDTOList;
    }

    public List<String> getAllEmailsByCity(String city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmailsByCity'");
    }

    public Person getPersonInfoByName(String firstName, String lastName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersonInfoByName'");
    }

    private boolean isAChild(Person person) {
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
