package com.safetynet.alertsapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.model.Person;

@Repository
public interface PersonRepository {

    List<Person> findAll();

    Person findByFirstnameAndLastname(String firstname, String lastname);

    void save(Person person);

    void update(Person updatePerson);

    void delete(Person person);

    List<Person> findByAddress(String address);

    List<Person> findByLastname(String lastname);

    List<Person> findByCity(String city);

}
