package com.safetynet.alertsapi.restController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alertsapi.model.Firestation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationRestControllerTest {

    private static final String FIRESTATION_URL = "/firestation";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    void testAddFirestation() throws Exception {
        Firestation newFirestation = new Firestation("123 Main St", "1");
        String newFirestationJson = mapper.writeValueAsString(newFirestation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newFirestationJson))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Firestation savedFirestation = mapper.readValue(content, Firestation.class);

        assertEquals("123 Main St", savedFirestation.getAddress());
        assertEquals("1", savedFirestation.getStation());
    }

    @Test
    void testDeleteFirestation() throws Exception {
        Firestation firestation = new Firestation("1509 Culver St", "3");
        String firestationJson = mapper.writeValueAsString(firestation);

        mockMvc.perform(MockMvcRequestBuilders.delete(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firestationJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateFirestation() throws Exception {
        Firestation firestation = new Firestation("29 15th St", "9");
        String firestationJson = mapper.writeValueAsString(firestation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firestationJson))
                .andExpect(status().isOk())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        Firestation savedFirestation = mapper.readValue(content, Firestation.class);

        assertEquals("29 15th St", savedFirestation.getAddress());
        assertEquals("9", savedFirestation.getStation());
    }

    @Test
    void testAddFirestationBadRequest() throws Exception {
        Firestation newFirestation = new Firestation(null, null);
        String newFirestationJson = mapper.writeValueAsString(newFirestation);

        mockMvc.perform(MockMvcRequestBuilders.post(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newFirestationJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteFirestationBadRequest() throws Exception {
        Firestation firestation = new Firestation(null, null);
        String firestationJson = mapper.writeValueAsString(firestation);

        mockMvc.perform(MockMvcRequestBuilders.delete(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firestationJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateFirestationBadRequest() throws Exception {
        Firestation firestation = new Firestation(null, null);
        String firestationJson = mapper.writeValueAsString(firestation);

        mockMvc.perform(MockMvcRequestBuilders.put(FIRESTATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firestationJson))
                .andExpect(status().isBadRequest());
    }
}
