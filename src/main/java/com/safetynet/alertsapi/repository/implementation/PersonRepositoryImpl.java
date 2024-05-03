package com.safetynet.alertsapi.repository.implementation;

import java.io.File;
import java.util.List;

import com.safetynet.alertsapi.config.constant.FilePathConstant;
import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.PersonRepository;
import com.safetynet.alertsapi.utils.LoaderUtils;

public class PersonRepositoryImpl implements PersonRepository {

    private List<Person> persons;

    public PersonRepositoryImpl() {
        persons = LoaderUtils.loadListFromFile(new File(FilePathConstant.JSON_FILE_PATH), Person.class, "persons");
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
            persons.add(person);
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

    // test
    public static void main(String[] args) {

        PersonRepository personRepository = new PersonRepositoryImpl();
        Person person = new Person();
        person.setFirstName("Max");
        person.setLastName("Test");
        person.setAddress("123 rue du test");
        person.setCity("Testcity");
        person.setZip("97300");
        person.setPhone("123-456-7890");
        person.setEmail("test@me.com");

        System.out.println("\n person not found by firstname and lastname : "
                + personRepository.findByFirstnameAndLastname("Max", "Test"));
        personRepository.save(person);
        System.out.println("\n Person found by firstname and lastname : "
                + personRepository.findByFirstnameAndLastname("Max", "Test"));
        // by adress
        System.out.println("\n Person found by address : " + personRepository.findByAddress("1509 Culver St"));
        System.out.println("\n Person not found by address : " + personRepository.findByAddress("pas d'address"));
        // by lastname
        System.out.println("\n Person found by lastname : " + personRepository.findByLastname("Boyd"));
        System.out.println("\n Person not found by lastname : " + personRepository.findByLastname("pas de nom"));
    }
}
