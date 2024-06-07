package com.safetynet.alertsapi.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.safetynet.alertsapi.dto.ResponseEmailDTO;
import com.safetynet.alertsapi.dto.ResponseFloodDTO;
import com.safetynet.alertsapi.dto.ResponsePhoneAlertDTO;
import com.safetynet.alertsapi.dto.medicalrecorddto.MedicalRecordBaseDTO;
import com.safetynet.alertsapi.dto.persondto.PersonInfoDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseChildAlertDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFireDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFirestationDTO;
import com.safetynet.alertsapi.dto.persondto.PersonResponseFloodDTO;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.MedicalRecordRepository;
import com.safetynet.alertsapi.service.PersonService;

public class DtoMapper {
    
    private static PersonService personService;
    private static MedicalRecordRepository medicalRecordRepository;
    
    public static PersonResponseFirestationDTO toPersonResponseFirestationDTO(Person person) {
        return new PersonResponseFirestationDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        );
    }

    public static List<PersonResponseFirestationDTO> toPersonResponseFirestationDTOList(List<Person> persons) {
        return persons.stream()
            .map(DtoMapper::toPersonResponseFirestationDTO)
            .toList();
    }

    public static PersonResponseChildAlertDTO toPersonResponseChildAlertDTO(Person person) {
        return new PersonResponseChildAlertDTO(
                person.getFirstName(),
                person.getLastName(),
                personService.getAge(medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                ))
        );
    }

    public static List<PersonResponseChildAlertDTO> toPersonResponseChildAlertDTOList(List<Person> persons) {
        return persons.stream()
            .map(DtoMapper::toPersonResponseChildAlertDTO)
            .toList();
    }

    public static ResponseEmailDTO toResponseEmailDTO(List<Person> personsByCity) {
        return new ResponseEmailDTO(personsByCity.stream()
                .map(p -> p.getEmail())
                .toList());
    }

    public static ResponsePhoneAlertDTO toResponsePhoneAlertDTO(List<Person> personsByStationNumber) {
        return new ResponsePhoneAlertDTO(personsByStationNumber.stream()
                .map(p -> p.getPhone())
                .toList());
    }

    public static PersonResponseFireDTO toPersonResponseFireDTO(Person person) {
        return new PersonResponseFireDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                personService.getAge(medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                )),
                toMedicalRecordBaseDTO(person)
        );
    }

    public static MedicalRecordBaseDTO toMedicalRecordBaseDTO(Person person) {
        return new MedicalRecordBaseDTO(
                medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                ).getMedications(),
                medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                ).getAllergies());
    }

    public static List<PersonResponseFireDTO> toPersonResponseFireDTOList(List<Person> persons) {
        return persons.stream()
            .map(DtoMapper::toPersonResponseFireDTO)
            .toList();
    }

    public static PersonResponseFloodDTO toPersonResponseFloodDTO(Person person) {
        return new PersonResponseFloodDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                personService.getAge(medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                )),
                toMedicalRecordBaseDTO(person)
        );
    }

    public static List<PersonResponseFloodDTO> toPersonResponseFloodDTOList(List<Person> persons) {
        return persons.stream()
            .map(DtoMapper::toPersonResponseFloodDTO)
            .toList();
    }

    public static Map<String, List<PersonResponseFloodDTO>> toMapPersonResponseFloodDTOList(List<Person> persons) {
        return persons.stream()
            .collect(Collectors.groupingBy(
                Person::getAddress,
                Collectors.mapping(DtoMapper::toPersonResponseFloodDTO, Collectors.toList())
            ));
    }

    public static List<ResponseFloodDTO> toResponseFloodDTOList(Map<String, List<PersonResponseFloodDTO>> personsByAddress) {
        return personsByAddress.entrySet().stream()
            .map(entry -> new ResponseFloodDTO(entry.getKey(), entry.getValue()))
            .toList();
    }

    public static PersonInfoDTO toPersonInfoDTO(Person person) {
        return new PersonInfoDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                personService.getAge(medicalRecordRepository.findByFirstnameAndLastname(
                        person.getFirstName(),
                        person.getLastName()
                )),
                person.getEmail(),
                toMedicalRecordBaseDTO(person)
        );
    }

    public static List<PersonInfoDTO> toPersonInfoDTOList(List<Person> persons) {
        return persons.stream()
            .map(DtoMapper::toPersonInfoDTO)
            .toList();
    }
}
