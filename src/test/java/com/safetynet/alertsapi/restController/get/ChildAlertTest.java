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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class ChildAlertTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /childAlert?address=1509 Culver St - OK
    // doit retourner ResponseChildAlertDTO
    @Test
    public void testGetChildAlert() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.CHILD + "?address=1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("children"));
        assertTrue(jsonObject.has("adults"));

        JSONArray childrenArray = jsonObject.getJSONArray("children");
        JSONArray adultsArray = jsonObject.getJSONArray("adults");

        // Vérifier que les tables ne sont pas vides
        assertFalse(childrenArray.length() == 0);
        assertFalse(adultsArray.length() == 0);

        // Vérifier la structure des objets dans children
        for (int i = 0; i < childrenArray.length(); i++) {
            JSONObject child = childrenArray.getJSONObject(i);
            assertTrue(child.has("firstName"));
            assertTrue(child.has("lastName"));
            assertTrue(child.has("age"));
            assertTrue(child.get("age") instanceof Integer);
        }

        // Vérifier la structure des objets dans adults
        for (int i = 0; i < adultsArray.length(); i++) {
            JSONObject adult = adultsArray.getJSONObject(i);
            assertTrue(adult.has("firstName"));
            assertTrue(adult.has("lastName"));
            assertTrue(adult.has("age"));
            assertTrue(adult.get("age") instanceof Integer);
        }
    }

    // GET /childAlert?address=Is Not A Valid Address - KO
    // doit retourner 404
    @Test
    public void testGetChildAlertAdressNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.CHILD + "?address=Is Not A Valid Address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // GET /childAlert?address=644 Gershwin Cir - OK
    // doit retourner liste vide car l'adresse n'a pas d'enfants
    @Test
    public void testGetChildAlertAdressIsEmpty() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(EndPointConstant.CHILD + "?address=644 Gershwin Cir")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.has("children"));
        assertTrue(jsonObject.has("adults"));

        // Vérifier que les tables sont vides
        JSONArray childrenArray = jsonObject.getJSONArray("children");
        JSONArray adultsArray = jsonObject.getJSONArray("adults");
        assertTrue(childrenArray.length() == 0);
        assertTrue(adultsArray.length() == 0);
    }
}
