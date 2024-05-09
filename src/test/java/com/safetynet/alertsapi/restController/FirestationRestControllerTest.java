package com.safetynet.alertsapi.restController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.alertsapi.controller.restcontroller.FirestationRestController;

@WebMvcTest(FirestationRestController.class)
public class FirestationRestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddFirestation() throws Exception {
        String newFirestationJson = "{\"address\": \"1509 Culver St\", \"station\": \"1\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newFirestationJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
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
