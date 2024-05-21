package com.safetynet.alertsapi.repository.implementation;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alertsapi.config.constant.FilePathConstant;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.PersonRepository;
import com.safetynet.alertsapi.utils.LoaderUtils;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private List<Person> persons;

    public PersonRepositoryImpl() {
        persons = LoaderUtils.loadListFromFile(new File(FilePathConstant.JSON_FILE_PATH), Person.class, "persons");
    }

    public PersonRepositoryImpl(List<Person> personsList) {
        this.persons = personsList;
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Person findByFirstnameAndLastname(String firstname, String lastname) {

        persons = findAll();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstname) && person.getLastName().equals(lastname)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public void save(Person newPerson) {
        persons.add(newPerson);
        // que faire en cas d'erreur ?
    }

    @Override
    public void update(Person updatePerson) {
        Person person = findByFirstnameAndLastname(updatePerson.getFirstName(), updatePerson.getLastName());
        if (person != null) {
            persons.remove(person);
            persons.add(updatePerson);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public void delete(Person deletePerson) {
        Person person = findByFirstnameAndLastname(deletePerson.getFirstName(), deletePerson.getLastName());
        if (person != null) {
            persons.remove(person);
        }
        // que faire en cas d'erreur ?
    }

    @Override
    public List<Person> findByAddress(String address) {
        persons = findAll();
        List<Person> personsByAddress = persons.stream().filter(p -> p.getAddress().equals(address)).toList();
        if (personsByAddress.isEmpty()) {
            return null;
        }
        return personsByAddress;
    }

    @Override
    public List<Person> findByLastname(String lastname) {
        persons = findAll();
        List<Person> personsByLastname = persons.stream().filter(p -> p.getLastName().equals(lastname)).toList();
        if (personsByLastname.isEmpty()) {
            return null;
        }
        return personsByLastname;
    }

    @Override
    public List<Person> findByCity(String city) {
        persons = findAll();
        List<Person> personsByCity = persons.stream().filter(p -> p.getCity().equals(city)).toList();
        if (personsByCity.isEmpty()) {
            return null;
        }
        return personsByCity;
    }
}
