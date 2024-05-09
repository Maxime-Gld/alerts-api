package com.safetynet.alertsapi.restController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.alertsapi.controller.restcontroller.MedicalRecordRestController;

@WebMvcTest(MedicalRecordRestController.class)
public class MedicalRecordRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddMedicalRecord() throws Exception {
        String newMedicalRecordJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newMedicalRecordJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testUpdateMedicalRecord() throws Exception {
        String updateMedicalRecordJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateMedicalRecordJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        String deleteMedicalRecordJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord")
            .contentType(MediaType.APPLICATION_JSON)
            .content(deleteMedicalRecordJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
