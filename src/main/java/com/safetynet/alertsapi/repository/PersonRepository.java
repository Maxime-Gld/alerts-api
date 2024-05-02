package com.safetynet.alertsapi.repository;

import java.util.List;

import com.safetynet.alertsapi.model.Person;

public interface PersonRepository {

    List<Person> findAll();

    Person findByFirstnameAndLastname(String firstname, String lastname);

    void save(Person person);

    void delete(Person person);

    List<Person> findByAddress(String address);

    List<Person> findByLastname(String lastname);

}
