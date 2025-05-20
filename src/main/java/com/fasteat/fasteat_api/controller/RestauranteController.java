package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import com.fasteat.fasteat_api.dto.RestauranteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Clase que define el controlador de restaurantes
 * Define los metodos para obtener, crear, actualizar y eliminar restaurantes   
 */

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestauranteRepository restauranteRepository;

    private RestauranteResponseDTO convertToDTO(Restaurante restaurante) {
        try {
            logger.debug("Convirtiendo restaurante a DTO: {}", restaurante.getIdRestaurante());
            RestauranteResponseDTO dto = new RestauranteResponseDTO(
                restaurante.getIdRestaurante(),
                restaurante.getNombre(),
                restaurante.getDireccion(),
                restaurante.getMenu()
            );
            logger.debug("DTO creado: {}", objectMapper.writeValueAsString(dto));
            return dto;
        } catch (Exception e) {
            logger.error("Error al convertir restaurante a DTO: ", e);
            throw new RuntimeException("Error al convertir restaurante a DTO", e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurantes() {
        logger.debug("Iniciando getAllRestaurantes");
        try {
            List<Restaurante> restaurantes = restauranteRepository.findAll();
            logger.debug("Restaurantes encontrados en BD: {}", restaurantes.size());
            
            List<RestauranteResponseDTO> restaurantesDTO = restaurantes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            logger.debug("Restaurantes convertidos a DTO: {}", restaurantesDTO.size());
            String jsonResponse = objectMapper.writeValueAsString(restaurantesDTO);
            logger.debug("Respuesta JSON generada: {}", jsonResponse);
            
            return ResponseEntity.ok(restaurantesDTO);
        } catch (Exception e) {
            logger.error("Error en getAllRestaurantes: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al obtener restaurantes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestauranteById(@PathVariable int id) {
        logger.debug("Iniciando getRestauranteById para id: {}", id);
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> {
                    logger.debug("Restaurante encontrado: {}", restaurante.getIdRestaurante());
                    return ResponseEntity.ok(convertToDTO(restaurante));
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error en getRestauranteById: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al obtener restaurante: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createRestaurante(@RequestBody Restaurante restaurante) {
        logger.debug("Iniciando createRestaurante");
        try {
            logger.debug("Datos recibidos: {}", objectMapper.writeValueAsString(restaurante));
            Restaurante savedRestaurante = restauranteRepository.save(restaurante);
            logger.debug("Restaurante guardado con id: {}", savedRestaurante.getIdRestaurante());
            return ResponseEntity.ok(convertToDTO(savedRestaurante));
        } catch (Exception e) {
            logger.error("Error en createRestaurante: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al crear restaurante: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable int id, @RequestBody Restaurante restauranteDetails) {
        logger.debug("Iniciando updateRestaurante para id: {}", id);
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> {
                    logger.debug("Restaurante encontrado para actualizar: {}", id);
                    restaurante.setNombre(restauranteDetails.getNombre());
                    restaurante.setDireccion(restauranteDetails.getDireccion());
                    restaurante.setMenu(restauranteDetails.getMenu());
                    Restaurante updatedRestaurante = restauranteRepository.save(restaurante);
                    logger.debug("Restaurante actualizado: {}", id);
                    return ResponseEntity.ok(convertToDTO(updatedRestaurante));
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error en updateRestaurante: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al actualizar restaurante: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable int id) {
        logger.debug("Iniciando deleteRestaurante para id: {}", id);
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restauranteRepository.delete(restaurante);
                    logger.debug("Restaurante eliminado: {}", id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error en deleteRestaurante: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al eliminar restaurante: " + e.getMessage());
        }
    }
}