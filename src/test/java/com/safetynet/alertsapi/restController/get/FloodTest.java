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
public class FloodTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /flood/stations?stations=<station_number_list> - OK
    // doit retourner ResponseFloodDTO
    @Test
    public void testGetFlood() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FLOOD + "?stations=1,2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            assertTrue(jsonObject.has("address"));
            assertTrue(jsonObject.has("persons"));

            JSONArray personsArray = jsonObject.getJSONArray("persons");

            for (int j = 0; j < personsArray.length(); j++) {
                JSONObject person = personsArray.getJSONObject(j);
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
    }

    // GET /flood/stations?stations=<station_number_list_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetFloodNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.FLOOD + "?stations=0,00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
