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

	public boolean isAChild(Person person) {
		MedicalRecord medicalRecord = getMedicalRecord(person);
		if (medicalRecord != null) {
			int age = getAge(person);
			return age < 18;
		}
		return false;
	}

	public int getAge(Person person) {
		MedicalRecord medicalRecord = getMedicalRecord(person);
		String birthDate = medicalRecord.getBirthdate();
		LocalDate birthdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		LocalDate now = LocalDate.now();
		return Period.between(birthdate, now).getYears();
	}

	public List<Person> getPersonsListByStationNumber(String stationNumber) {
		List<Firestation> firestationsBySationNumber = firestationRepository.findByStationNumber(stationNumber);
		List<Person> personsByStationNumber = new ArrayList<>();

		for (Firestation firestation : firestationsBySationNumber) {
			List<Person> persons = (personRepository.findByAddress(firestation.getAddress()));
			personsByStationNumber.addAll(persons);
		}
		return personsByStationNumber;
	}

	public List<Person> getPersonsListByAddress(String address) {
		return personRepository.findByAddress(address);
	}

	public List<Person> getChildrenByAdress(List<Person> persons) {
		return persons.stream().filter(this::isAChild).toList();
	}

	public List<Person> getAdultsByAdress(List<Person> persons) {
		return persons.stream().filter(p -> !isAChild(p)).toList();
	}

	public Firestation getFirestationByAddress(String address) {
		return firestationRepository.findByAddress(address);
	}

	public List<Person> findByLastname(String lastName) {
		return personRepository.findByLastname(lastName);
	}

	public List<Person> getPersonsByCity(String city) {
		return personRepository.findByCity(city);
	}

	public MedicalRecord getMedicalRecord(Person person) {
		return medicalRecordRepository.findByFirstnameAndLastname(person.getFirstName(), person.getLastName());
	}
}
