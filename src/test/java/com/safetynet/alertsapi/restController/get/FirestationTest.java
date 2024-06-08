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
public class FirestationTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /firestation?stationNumber=<station_number> - OK
    // doit retourner ResponseFirestationDTO
    @Test
    public void testGetFirestation() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FIRESTATION + "?stationNumber=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content); // Convertir la chaîne JSON en objet JSON

        assertTrue(jsonObject.has("childCount"));
        assertTrue(jsonObject.has("adultCount"));
        assertTrue(jsonObject.has("persons"));

        JSONArray personsArray = jsonObject.getJSONArray("persons");

        for (int i = 0; i < personsArray.length(); i++) {
            JSONObject person = personsArray.getJSONObject(i);
            assertTrue(person.has("firstName"));
            assertTrue(person.has("lastName"));
            assertTrue(person.has("address"));
            assertTrue(person.has("phone"));
        }
    }

    // GET /firestation?stationNumber=<station_number_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetFirestationNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FIRESTATION + "?stationNumber=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
