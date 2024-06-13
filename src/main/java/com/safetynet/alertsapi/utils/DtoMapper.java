package com.safetynet.alertsapi.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.safetynet.alertsapi.service.PersonService;

@Component
/**
 * Classe permettant de mapper les objets de type Person vers les objets de type
 * DTO
 */
public class DtoMapper {

        private PersonService personService;

        @Autowired
        public DtoMapper(PersonService personService) {
                this.personService = personService;
        }

        public PersonResponseFirestationDTO toPersonResponseFirestationDTO(Person person) {
                return new PersonResponseFirestationDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone());
        }

        public List<PersonResponseFirestationDTO> toPersonResponseFirestationDTOList(List<Person> persons) {
                return persons.stream()
                                .map(this::toPersonResponseFirestationDTO)
                                .toList();
        }

        public PersonResponseChildAlertDTO toPersonResponseChildAlertDTO(Person person) {
                return new PersonResponseChildAlertDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                personService.getAge(person));
        }

        public List<PersonResponseChildAlertDTO> toPersonResponseChildAlertDTOList(List<Person> persons) {
                return persons.stream()
                                .map(this::toPersonResponseChildAlertDTO)
                                .toList();
        }

        public ResponseEmailDTO toResponseEmailDTO(List<Person> personsByCity) {
                return new ResponseEmailDTO(personsByCity.stream()
                                .map(p -> p.getEmail())
                                .toList());
        }

        public ResponsePhoneAlertDTO toResponsePhoneAlertDTO(List<Person> personsByStationNumber) {
                return new ResponsePhoneAlertDTO(personsByStationNumber.stream()
                                .map(p -> p.getPhone())
                                .toList());
        }

        public PersonResponseFireDTO toPersonResponseFireDTO(Person person) {
                return new PersonResponseFireDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                personService.getAge(person),
                                toMedicalRecordBaseDTO(person));
        }

        public MedicalRecordBaseDTO toMedicalRecordBaseDTO(Person person) {
                return new MedicalRecordBaseDTO(
                                personService.getMedicalRecord(person).getMedications(),
                                personService.getMedicalRecord(person).getAllergies());
        }

        public List<PersonResponseFireDTO> toPersonResponseFireDTOList(List<Person> persons) {
                return persons.stream()
                                .map(this::toPersonResponseFireDTO)
                                .toList();
        }

        public PersonResponseFloodDTO toPersonResponseFloodDTO(Person person) {
                return new PersonResponseFloodDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                personService.getAge(person),
                                toMedicalRecordBaseDTO(person));
        }

        public Map<String, List<PersonResponseFloodDTO>> toMapPersonResponseFloodDTOList(List<Person> persons) {
                return persons.stream()
                                .collect(Collectors.groupingBy(
                                                Person::getAddress,
                                                Collectors.mapping(this::toPersonResponseFloodDTO,
                                                                Collectors.toList())));
        }

        public List<ResponseFloodDTO> toResponseFloodDTOList(
                        Map<String, List<PersonResponseFloodDTO>> personsByAddress) {
                return personsByAddress.entrySet().stream()
                                .map(entry -> new ResponseFloodDTO(entry.getKey(), entry.getValue()))
                                .toList();
        }

        public PersonInfoDTO toPersonInfoDTO(Person person) {
                return new PersonInfoDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                personService.getAge(person),
                                person.getEmail(),
                                toMedicalRecordBaseDTO(person));
        }

        public List<PersonInfoDTO> toPersonInfoDTOList(List<Person> persons) {
                return persons.stream()
                                .map(this::toPersonInfoDTO)
                                .toList();
        }
}
