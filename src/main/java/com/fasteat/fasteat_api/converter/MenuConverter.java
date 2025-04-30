package com.fasteat.fasteat_api.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/*
 * Clase que permite convertir un mapa a un JSON y viceversa
 */
@Converter
public class MenuConverter implements AttributeConverter<Map<String, Double>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Double> menu) {
        try {
            return objectMapper.writeValueAsString(menu);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Double> convertToEntityAttribute(String menuJson) {
        try {
            return objectMapper.readValue(menuJson, HashMap.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }
}