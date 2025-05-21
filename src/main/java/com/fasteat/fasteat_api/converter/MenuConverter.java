package com.fasteat.fasteat_api.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/*
 * Clase que permite convertir un mapa a un JSON y viceversa
 * El mapa usa el ID del producto como clave y el precio como valor
 */
@Converter
public class MenuConverter implements AttributeConverter<Map<Integer, Double>, String> {

    private static final Logger logger = LoggerFactory.getLogger(MenuConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Integer, Double> menu) {
        if (menu == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(menu);
        } catch (JsonProcessingException e) {
            logger.error("Error converting menu map to JSON", e);
            return "{}";
        }
    }

    @Override
    public Map<Integer, Double> convertToEntityAttribute(String menuJson) {
        if (menuJson == null || menuJson.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(menuJson, HashMap.class);
        } catch (IOException e) {
            logger.error("Error converting JSON to menu map", e);
            return new HashMap<>();
        }
    }
}