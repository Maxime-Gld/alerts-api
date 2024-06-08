package com.safetynet.alertsapi.restController.get;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alertsapi.config.constant.EndPointConstant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonInfoLastNameTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /personInfolastName?lastName=<last_name> - OK
    // doit retourner ResponsePersonInfoDTO
    @Test
    public void testGetPersonInfo() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.PERSON_INFO + "?lastName=Boyd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("persons"));

        JSONArray personsArray = jsonObject.getJSONArray("persons");
        for (int i = 0; i < personsArray.length(); i++) {
            JSONObject person = personsArray.getJSONObject(i);
            assertTrue(person.has("firstName"));
            assertTrue(person.has("lastName"));
            assertTrue(person.has("address"));
            assertTrue(person.has("age"));
            assertTrue(person.get("age") instanceof Integer);
            assertTrue(person.has("medicalRecord"));

            JSONObject medicalRecord = person.getJSONObject("medicalRecord");
            assertTrue(medicalRecord.has("allergies"));
            assertTrue(medicalRecord.has("medications"));
        }
    }

    // GET /personInfolastName?lastName=<last_name_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetPersonInfoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.PERSON_INFO + "?lastName=unknown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
