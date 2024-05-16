package com.safetynet.alertsapi.restController;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.alertsapi.model.Firestation;
import com.safetynet.alertsapi.repository.FirestationRepository;
import com.safetynet.alertsapi.repository.implementation.FirestationRepositoryImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationRestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FirestationRepository firestationRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testAddFirestation() throws Exception {

        List<Firestation> firestationsBeforeTest = firestationRepository.findAll();
        String newFirestationJson = "{\"address\": \"test\", \"station\": \"40\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newFirestationJson))
            .andExpect(MockMvcResultMatchers.status().isOk());

        List<Firestation> firestations = firestationRepository.findAll();

        // TODO : vérifier que le nombre de firestation est bien +1 (changer la facon de faire)
            
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        String updateFirestationJson = "{\"address\": \"1509 Culver St\", \"station\": \"1\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateFirestationJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        String deleteFirestationJson = "{\"address\": \"1509 Culver St\", \"station\": \"1\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(deleteFirestationJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
