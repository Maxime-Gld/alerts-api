package com.safetynet.alertsapi.utils;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoaderUtils {

    public static <T> List<T> loadListFromFile(File data, Class<T> clazz, String listname) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            JsonNode listNode = jsonNode.get(listname);

            return objectMapper.convertValue(listNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
