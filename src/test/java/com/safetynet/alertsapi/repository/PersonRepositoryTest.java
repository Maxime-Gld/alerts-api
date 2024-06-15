package com.safetynet.alertsapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alertsapi.model.Person;
import com.safetynet.alertsapi.repository.implementation.PersonRepositoryImpl;

public class PersonRepositoryTest {

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        List<Person> persons = new ArrayList<>(Arrays.asList(
                new Person("John", "Doe", "123 Main St", "New York", "10001", "123-456-7890", "jdoe@me.com"),
                new Person("Bob", "Doe", "124 Main St", "New York", "10001", "123-456-7899", "bdoe@me.com"),
                new Person("Jane", "Smith", "456 Elm St", "Los Angeles", "90001", "987-654-3210", "jsmith@me.com"),
                new Person("Alice", "Johnson", "789 Oak St", "Chicago", "60601", "456-789-0123", "ajohnson@me.com"),
                new Person("Mark", "Jobert", "789 Oak St", "Chicago", "60601", "456-789-0123", "mjobert@me.com")));
        personRepository = new PersonRepositoryImpl(persons);
    }

    @Test
    void testFindAll() {
        // Given (setUp)

        // When
        List<Person> persons = personRepository.findAll();

        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
    }

    @Test
    void testFindByFirstnameAndLastname() {
        // Given (setUp)
        String firstname = "John";
        String lastname = "Doe";

        // When
        Person foundPerson = personRepository.findByFirstnameAndLastname(firstname, lastname);

        // Then
        assertNotNull(foundPerson);
        assertEquals(firstname, foundPerson.getFirstName());
        assertEquals(lastname, foundPerson.getLastName());
    }

    @Test
    void testFindByFirstnameAndLastnameNotFound() {
        // Given (setUp)
        String firstname = "Jane";
        String lastname = "Doe";

        // When
        Person notFoundPerson = personRepository.findByFirstnameAndLastname(firstname, lastname);

        // Then
        assertNull(notFoundPerson);

    }

    @Test
    void testSave() {
        // Given
        Person newPerson = new Person("Max", "Test", "123 rue du test", "Testcity", "97300", "123-456-7890",
                "test@me.com");
        // When
        personRepository.save(newPerson);
        List<Person> persons = personRepository.findAll();
        // Then
        assertTrue(persons.contains(newPerson));
    }

    @Test
    void testUpdate() {
        // Given
        List<Person> persons = personRepository.findAll();
        Person personBeforeUpdate = persons.get(0); // John Doe
        Person updatePerson = new Person("John", "Doe", "123 Rue du test", "Test city", "10510", "123-456-7899",
                "jdoe@me.com");
        // When
        personRepository.update(updatePerson);

        // Then
        assertFalse(persons.contains(personBeforeUpdate));
        assertTrue(persons.contains(updatePerson));
    }

    @Test
    void testDelete() {
        // Given
        List<Person> persons = personRepository.findAll();
        Person personToDelete = persons.get(0); // John Doe
        // When
        personRepository.delete(personToDelete);

        // Then
        assertFalse(persons.contains(personToDelete));
    }

    @Test
    void testFindByAddress() {
        // Given
        String address = "789 Oak St";
        // When
        List<Person> persons = personRepository.findByAddress(address);
        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(address, persons.get(0).getAddress());
    }

    @Test
    void testFindByAddressNotFound() {
        // Given
        String address = "123 null part";

        // When
        List<Person> persons = personRepository.findByAddress(address);

        // Then
        assertNull(persons);
    }

    @Test
    void testFindByLastname() {
        // Given
        String lastname = "Doe";

        // When
        List<Person> persons = personRepository.findByLastname(lastname);

        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(lastname, persons.get(0).getLastName());
    }

    @Test
    void testFindByLastnameNotFound() {
        // Given
        String lastname = "Test";

        // When
        List<Person> persons = personRepository.findByLastname(lastname);

        // Then
        assertNull(persons);
    }
}
