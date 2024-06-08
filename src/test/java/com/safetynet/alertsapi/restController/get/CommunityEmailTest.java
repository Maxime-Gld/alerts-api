package com.safetynet.alertsapi.restController.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alertsapi.config.constant.EndPointConstant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class CommunityEmailTest {
    
    @Autowired
    private MockMvc mockMvc;

    // GET /communityEmail?city=<city> - OK
    // doit retourner ResponseCommunityEmailDTO
    @Test
    public void testGetCommunityEmail() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.EMAIL + "?city=Culver")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("emails"));
    }

    // GET /communityEmail?city=<city_invalid> - KO
    // doit retourner 404
    @Test
    public void testGetCommunityEmailNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.EMAIL + "?city=invalid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
