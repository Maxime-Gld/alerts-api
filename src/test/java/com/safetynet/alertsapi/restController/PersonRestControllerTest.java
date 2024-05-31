package com.safetynet.alertsapi.restController;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alertsapi.model.DataJson;
import com.safetynet.alertsapi.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        loadTestData();
    }

    private List<Person> loadTestData() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dataTest.json");
        DataJson dataJson = mapper.readValue(inputStream, DataJson.class);
        return dataJson.getPersons();
    }   
    
    @Test
    public void testAddPerson() throws Exception {
        Person newPerson = new Person("John", "Doe", "123 address", "city", "zip", "phone", "email");
        String newPersonJson = mapper.writeValueAsString(newPerson);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newPersonJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        Person savedPerson = mapper.readValue(content, Person.class);

        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        assertEquals("123 address", savedPerson.getAddress());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person updatePerson = new Person("John", "Boyd", "123 address", "city", "zip", "phone", "email");
        String updatePersonJson = mapper.writeValueAsString(updatePerson);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatePersonJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        Person savedPerson = mapper.readValue(content, Person.class);

        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Boyd", savedPerson.getLastName());
        assertEquals("123 address", savedPerson.getAddress());
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person person = new Person("John", "Boyd", "123 address", "city", "zip", "phone", "email");
        String deletePersonJson = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(deletePersonJson))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testAddPersonBadRequest() throws Exception {
        Person person = new Person(null, null, "test", "test", "test", "test", "test");
        String personJson = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(personJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdatePersonBadRequest() throws Exception {
        Person person = new Person("test", "test", "test", "test", "test", "test", "test");
        String personJson = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(personJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeletePersonBadRequest() throws Exception {
        Person person = new Person("test", "test", "test", "test", "test", "test", "test");
        String personJson = mapper.writeValueAsString(person);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(personJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
