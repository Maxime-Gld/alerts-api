package com.safetynet.alertsapi.restController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.alertsapi.controller.restcontroller.PersonRestController;

@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testAddPerson() throws Exception {
        String newPersonJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newPersonJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        String updatePersonJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 30}";

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatePersonJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        String deletePersonJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(deletePersonJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
