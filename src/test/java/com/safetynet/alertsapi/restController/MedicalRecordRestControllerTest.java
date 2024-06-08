package com.safetynet.alertsapi.restController;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.safetynet.alertsapi.model.MedicalRecord;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordRestControllerTest {

    private static final String MEDICAL_RECORD_URL = "/medicalrecord";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord newMedicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("med1"), List.of("all1"));  
        String newMedicalRecordJson = mapper.writeValueAsString(newMedicalRecord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMedicalRecordJson))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MedicalRecord savedMedicalRecord = mapper.readValue(content, MedicalRecord.class);
        assertEquals("John", savedMedicalRecord.getFirstName());
        assertEquals("Doe", savedMedicalRecord.getLastName());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord updateMedicalRecord = new MedicalRecord("Jacob", "Boyd", "01/01/2000", List.of("med1"), List.of("all1"));
        String updateMedicalRecordJson = mapper.writeValueAsString(updateMedicalRecord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateMedicalRecordJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MedicalRecord savedMedicalRecord = mapper.readValue(content, MedicalRecord.class);
        
        assertEquals("Jacob", savedMedicalRecord.getFirstName());
        assertEquals("Boyd", savedMedicalRecord.getLastName());
        assertEquals("01/01/2000", savedMedicalRecord.getBirthdate());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        MedicalRecord deleteMedicalRecord = new MedicalRecord("John", "Boyd", "01/01/2000", List.of("med1"), List.of("all1"));
        String deleteMedicalRecordJson = mapper.writeValueAsString(deleteMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.delete(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(deleteMedicalRecordJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testAddMedicalRecordBadRequest() throws Exception {
        MedicalRecord newMedicalRecord = new MedicalRecord(null, null, null, null, null);
        String newMedicalRecordJson = mapper.writeValueAsString(newMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.post(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMedicalRecordJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateMedicalRecordBadRequest() throws Exception {
        MedicalRecord updateMedicalRecord = new MedicalRecord(null, null, null, null, null);
        String updateMedicalRecordJson = mapper.writeValueAsString(updateMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.put(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateMedicalRecordJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecordBadRequest() throws Exception {
        MedicalRecord deleteMedicalRecord = new MedicalRecord(null, null, null, null, null);
        String deleteMedicalRecordJson = mapper.writeValueAsString(deleteMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.delete(MEDICAL_RECORD_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(deleteMedicalRecordJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
