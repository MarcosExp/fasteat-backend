package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import com.fasteat.fasteat_api.dto.RestauranteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Autowired
    private RestauranteRepository restauranteRepository;

    private RestauranteResponseDTO convertToDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(
            restaurante.getIdRestaurante(),
            restaurante.getNombre(),
            restaurante.getDireccion(),
            restaurante.getMenu()
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurantes() {
        try {
            List<Restaurante> restaurantes = restauranteRepository.findAll();
            List<RestauranteResponseDTO> restaurantesDTO = restaurantes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            logger.info("Encontrados {} restaurantes", restaurantes.size());
            return ResponseEntity.ok(restaurantesDTO);
        } catch (Exception e) {
            logger.error("Error al obtener restaurantes: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al obtener restaurantes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestauranteById(@PathVariable int id) {
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> ResponseEntity.ok(convertToDTO(restaurante)))
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error al obtener restaurante con id {}: ", id, e);
            return ResponseEntity.internalServerError()
                .body("Error al obtener restaurante: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createRestaurante(@RequestBody Restaurante restaurante) {
        try {
            Restaurante savedRestaurante = restauranteRepository.save(restaurante);
            logger.info("Restaurante creado con id: {}", savedRestaurante.getIdRestaurante());
            return ResponseEntity.ok(convertToDTO(savedRestaurante));
        } catch (Exception e) {
            logger.error("Error al crear restaurante: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al crear restaurante: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable int id, @RequestBody Restaurante restauranteDetails) {
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restaurante.setNombre(restauranteDetails.getNombre());
                    restaurante.setDireccion(restauranteDetails.getDireccion());
                    restaurante.setMenu(restauranteDetails.getMenu());
                    Restaurante updatedRestaurante = restauranteRepository.save(restaurante);
                    logger.info("Restaurante actualizado con id: {}", id);
                    return ResponseEntity.ok(convertToDTO(updatedRestaurante));
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error al actualizar restaurante con id {}: ", id, e);
            return ResponseEntity.internalServerError()
                .body("Error al actualizar restaurante: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable int id) {
        try {
            return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restauranteRepository.delete(restaurante);
                    logger.info("Restaurante eliminado con id: {}", id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error al eliminar restaurante con id {}: ", id, e);
            return ResponseEntity.internalServerError()
                .body("Error al eliminar restaurante: " + e.getMessage());
        }
    }
}