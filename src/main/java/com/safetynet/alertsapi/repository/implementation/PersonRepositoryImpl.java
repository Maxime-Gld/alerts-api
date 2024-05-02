package com.safetynet.alertsapi.repository.implementation;

import java.util.List;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.PersonRepository;

public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public List<Person> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person findByFirstnameAndLastname(String firstname, String lastname) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Person person) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Person person) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Person> findByAddress(String address) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Person> findByLastname(String lastname) {
        // TODO Auto-generated method stub
        return null;
    }
}
