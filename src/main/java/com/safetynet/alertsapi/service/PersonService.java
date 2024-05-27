package com.safetynet.alertsapi.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.alertsapi.dto.ResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.ResponseEmailDTO;
import com.safetynet.alertsapi.dto.ResponseFireDTO;
import com.safetynet.alertsapi.dto.ResponseFirestationDTO;
import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.ResponsePersonInfoDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;
import com.safetynet.alertsapi.dto.persondto.PersonInfoDTO;
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

		List<Person> personsByStationNumber = getPersonsListByStationNumber(stationNumber);

		for (Person person : personsByStationNumber) {
			if (isAChild(person)) {
				child++;
			} else {
				adult++;
			}
		}

		List<PersonResponseFirestationDTO> personsInformations = personsByStationNumber.stream()
				.map(p -> new PersonResponseFirestationDTO(
						p.getFirstName(),
						p.getLastName(),
						p.getAddress(),
						p.getPhone()))
				.toList();


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

		List<Person> personsByStationNumber = getPersonsListByStationNumber(stationNumber);
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

	public List<ResponseFloodDTO> getHouseholdInformationsByStations(List<Integer> stationNumbers) {
		List<ResponseFloodDTO> householdInformationsList = new ArrayList<>();
		for (Integer stationNumber : stationNumbers) {
			List<Person> personsByStationNumber = getPersonsListByStationNumber(stationNumber.toString());
			Map<String, List<PersonResponseFloodDTO>> personsByAddress = mapPersonsToAddressAndPersonResponseFloodDTO(personsByStationNumber);
			List<ResponseFloodDTO> householdInformations = createResponseFloodDTOList(personsByAddress);
            householdInformationsList.addAll(householdInformations);
		}
		
		return householdInformationsList;
	}

	private List<ResponseFloodDTO> createResponseFloodDTOList(Map<String, List<PersonResponseFloodDTO>> personsByAddress) {
        return personsByAddress.entrySet().stream()
                .map(entry -> new ResponseFloodDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

	private Map<String, List<PersonResponseFloodDTO>> mapPersonsToAddressAndPersonResponseFloodDTO(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(
                        Person::getAddress,
                        Collectors.mapping(this::mapPersonToPersonResponseFloodDTO, Collectors.toList())
                ));

    }

	private PersonResponseFloodDTO mapPersonToPersonResponseFloodDTO(Person person) {
		PersonResponseFloodDTO personsInformation = new PersonResponseFloodDTO(
						person.getFirstName(),
						person.getLastName(),
						person.getPhone(),
						getAge(medicalRecordRepository.findByFirstnameAndLastname(
								person.getFirstName(),
								person.getLastName())),
						new MedicalRecordBaseDTO(
								medicalRecordRepository.findByFirstnameAndLastname(
										person.getFirstName(),
										person.getLastName()).getMedications(),
								medicalRecordRepository.findByFirstnameAndLastname(
										person.getFirstName(),
										person.getLastName()).getAllergies()));
		return personsInformation;
	}

	public ResponsePersonInfoDTO getPersonInfoByName(String firstName, String lastName) {

		List<Person> persons = personRepository.findByLastname(lastName);

		if (persons == null) {
			return null;
		}

		return new ResponsePersonInfoDTO(persons.stream().map(p -> new PersonInfoDTO(
				p.getFirstName(),
				p.getLastName(),
				p.getAddress(),
				getAge(medicalRecordRepository.findByFirstnameAndLastname(
						p.getFirstName(),
						p.getLastName())),
				p.getEmail(),
				new MedicalRecordBaseDTO(
						medicalRecordRepository.findByFirstnameAndLastname(
								p.getFirstName(),
								p.getLastName()).getMedications(),
						medicalRecordRepository.findByFirstnameAndLastname(
								p.getFirstName(),
								p.getLastName()).getAllergies())))
				.toList());
	}

	public ResponseEmailDTO getAllEmailsByCity(String city) {
		List<Person> personsByCity = personRepository.findByCity(city);
		return new ResponseEmailDTO(personsByCity.stream()
				.map(p -> p.getEmail())
				.toList());
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
		return Period.between(birthdate, now).getYears();
	}

	private List<Person> getPersonsListByStationNumber(String stationNumber) {
		List<Firestation> firestationsBySationNumber = firestationRepository.findByStationNumber(stationNumber);
		List<Person> personsByStationNumber = new ArrayList<>();

		for (Firestation firestation : firestationsBySationNumber) {
			List<Person> persons = (personRepository.findByAddress(firestation.getAddress()));
			personsByStationNumber.addAll(persons);
		}
		return personsByStationNumber;
	}

}
