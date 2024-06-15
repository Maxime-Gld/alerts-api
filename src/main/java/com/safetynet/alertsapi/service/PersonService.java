package com.safetynet.alertsapi.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

    /**
     * verifie si une personne est un enfant
     * 
     * @param person la personne
     * @return true si l'age de la personne est inferieur ou égale à 18
     * @return false si l'age de la personne est supérieur à 18
     */
    public boolean isAChild(Person person) {
        MedicalRecord medicalRecord = getMedicalRecord(person);
        if (medicalRecord != null) {
            int age = getAge(person);
            return age <= 18;
        }
        return false;
    }

    /**
     * calcule l'age d'une personne a partir de sa date de naissance récupérée dans
     * son dossier medical et la date actuelle
     * 
     * @param person les informations de la personne
     * @return l'age de la personne
     */
    public int getAge(Person person) {
        MedicalRecord medicalRecord = getMedicalRecord(person);
        String birthDate = medicalRecord.getBirthdate();
        LocalDate birthdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate now = LocalDate.now();
        return Period.between(birthdate, now).getYears();
    }

    /**
     * Récupère la liste des personnes couvertes par une station
     * 
     * @param stationNumber le numéro de la station
     * @return la liste des personnes
     */
    public List<Person> getPersonsListByStationNumber(String stationNumber) {
        List<Firestation> firestationsBySationNumber = firestationRepository.findByStationNumber(stationNumber);
        List<Person> personsByStationNumber = new ArrayList<>();

        for (Firestation firestation : firestationsBySationNumber) {
            List<Person> persons = (personRepository.findByAddress(firestation.getAddress()));
            personsByStationNumber.addAll(persons);
        }
        return personsByStationNumber;
    }

    /**
     * Récupère la liste des personnes par adresse
     * 
     * @param address l'adresse que la/les personnes doivent avoir
     * @return la liste des personnes
     */
    public List<Person> getPersonsListByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    /**
     * Récupère une liste des enfants présents dans la liste de personnes
     * 
     * @param persons la liste des personnes
     * @return la liste des enfants
     */
    public List<Person> getChildrenByAdress(List<Person> persons) {
        return persons.stream().filter(this::isAChild).toList();
    }

    /**
     * Récupère une liste des adultes présents dans la liste de personnes
     * 
     * @param persons la liste des personnes
     * @return la liste des adultes
     */
    public List<Person> getAdultsByAdress(List<Person> persons) {
        return persons.stream().filter(p -> !isAChild(p)).toList();
    }

    /**
     * Récupère une station par son adresse
     * 
     * @param address l'adresse de la station
     * @return la station
     */
    public Firestation getFirestationByAddress(String address) {
        return firestationRepository.findByAddress(address);
    }

    /**
     * Récupère une liste de personnes portant le même nom
     * 
     * @param lastName le nom que la/les personnes doivent avoir
     * @return la liste des personnes
     */
    public List<Person> findByLastname(String lastName) {
        return personRepository.findByLastname(lastName);
    }

    /**
     * Récupère une liste de personnes se trouvant dans une ville
     * 
     * @param city la ville ou la/les personnes doivent se trouver
     * @return la liste des personnes
     */
    public List<Person> getPersonsByCity(String city) {
        return personRepository.findByCity(city);
    }

    /**
     * Récupère le dossier medical d'une personne
     * 
     * @param person la personne
     * @return le dossier medical
     */
    public MedicalRecord getMedicalRecord(Person person) {
        return medicalRecordRepository.findByFirstnameAndLastname(person.getFirstName(), person.getLastName());
    }
}
