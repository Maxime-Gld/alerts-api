package com.safetynet.alertsapi.restController.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class FireTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /fire?address=<address> - OK
    // doit retourner ResponseFireDTO
    @Test
    public void testGetFire() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FIRE + "?address=1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("firestation"));

        JSONObject firestation = jsonObject.getJSONObject("firestation");
        assertTrue(firestation.has("address"));
        assertTrue(firestation.has("station"));
        assertEquals("1509 Culver St", firestation.getString("address"));

        assertTrue(jsonObject.has("persons"));

        JSONArray personsArray = jsonObject.getJSONArray("persons");
        for (int i = 0; i < personsArray.length(); i++) {
            JSONObject person = personsArray.getJSONObject(i);
            assertTrue(person.has("firstName"));
            assertTrue(person.has("lastName"));
            assertTrue(person.has("phone"));
            assertTrue(person.has("age"));
            assertTrue(person.get("age") instanceof Integer);
            assertTrue(person.has("medicalRecord"));

            JSONObject medicalRecord = person.getJSONObject("medicalRecord");
            assertTrue(medicalRecord.has("allergies"));
            assertTrue(medicalRecord.has("medications"));
        }
    }

    // GET /fire?address=<address_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetFireNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FIRE + "?address=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
