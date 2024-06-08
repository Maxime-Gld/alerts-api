package com.safetynet.alertsapi.restController.get;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class PhoneAlertTest {
    
    @Autowired
    private MockMvc mockMvc;

    // GET /phoneAlert?firestation=<firestation_number> - OK
    // doit retourner ResponsePhoneAlertDTO
    @Test
    public void testGetPhoneAlert() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.PHONE + "?firestation=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("phoneNumbers"));
    }

    // GET /phoneAlert?firestation=<firestation_number_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetPhoneAlertNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.PHONE + "firestation=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
