package com.safetynet.alertsapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.model.Person;

@Repository
public interface PersonRepository {

    List<Person> findAll();

    Person findByFirstnameAndLastname(String firstname, String lastname);

    Person save(Person person);

    Person update(Person updatePerson);

    boolean delete(Person person);

    List<Person> findByAddress(String address);

    List<Person> findByLastname(String lastname);

    List<Person> findByCity(String city);

}
