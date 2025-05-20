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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Clase que define el controlador de restaurantes
 * Define los metodos para obtener, crear, actualizar y eliminar restaurantes   
 */

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurante", description = "API para la gestión de restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestauranteRepository restauranteRepository;

    private RestauranteResponseDTO convertToDTO(Restaurante restaurante) {
        try {
            logger.debug("Iniciando conversión de restaurante a DTO");
            logger.debug("ID del restaurante: {}", restaurante.getIdRestaurante());
            logger.debug("Nombre del restaurante: {}", restaurante.getNombre());
            logger.debug("Dirección del restaurante: {}", restaurante.getDireccion());

            RestauranteResponseDTO dto = new RestauranteResponseDTO();
            dto.setIdRestaurante(restaurante.getIdRestaurante());
            dto.setNombre(restaurante.getNombre());
            dto.setDireccion(restaurante.getDireccion());
            
            // Convertir el menú a String
            String menuJson = objectMapper.writeValueAsString(restaurante.getMenu());
            dto.setMenu(menuJson);

            logger.debug("DTO creado exitosamente");
            return dto;
        } catch (Exception e) {
            logger.error("Error detallado al convertir restaurante a DTO: ", e);
            throw new RuntimeException("Error al convertir restaurante a DTO: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Obtener todos los restaurantes", description = "Retorna una lista de todos los restaurantes registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de restaurantes encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> getAllRestaurantes() {
        logger.debug("Iniciando getAllRestaurantes");
        try {
            List<Restaurante> restaurantes = restauranteRepository.findAll();
            logger.debug("Restaurantes encontrados en BD: {}", restaurantes.size());
            
            if (restaurantes.isEmpty()) {
                logger.debug("No se encontraron restaurantes");
                return ResponseEntity.ok(List.of());
            }

            List<RestauranteResponseDTO> restaurantesDTO = restaurantes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            logger.debug("Restaurantes convertidos a DTO: {}", restaurantesDTO.size());
            return ResponseEntity.ok(restaurantesDTO);
        } catch (Exception e) {
            logger.error("Error en getAllRestaurantes: ", e);
            return ResponseEntity.internalServerError()
                .body("Error al obtener restaurantes: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener restaurante por ID", description = "Retorna un restaurante específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestauranteById(
            @Parameter(description = "ID del restaurante a buscar") @PathVariable int id) {
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

    @Operation(summary = "Crear nuevo restaurante", description = "Crea un nuevo restaurante en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante creado exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createRestaurante(
            @Parameter(description = "Datos del restaurante a crear") @RequestBody Restaurante restaurante) {
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

    @Operation(summary = "Actualizar restaurante", description = "Actualiza los datos de un restaurante existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurante(
            @Parameter(description = "ID del restaurante a actualizar") @PathVariable int id,
            @Parameter(description = "Nuevos datos del restaurante") @RequestBody Restaurante restauranteDetails) {
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

    @Operation(summary = "Eliminar restaurante", description = "Elimina un restaurante del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurante(
            @Parameter(description = "ID del restaurante a eliminar") @PathVariable int id) {
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