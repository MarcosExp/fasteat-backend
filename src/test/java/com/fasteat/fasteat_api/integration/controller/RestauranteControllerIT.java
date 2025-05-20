package com.fasteat.fasteat_api.integration.controller;

import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RestauranteControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/restaurantes";
    }

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        restauranteRepository.deleteAll();
    }

    @Test
    void testCrearYObtenerRestaurante() {
        // Crear restaurante
        Map<String, Double> menu = new HashMap<>();
        menu.put("Hamburguesa", 8.99);
        menu.put("Pizza", 12.99);
        
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("La Buena Mesa");
        restaurante.setDireccion("Calle Principal 123");
        restaurante.setMenu(menu);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Restaurante> entity = new HttpEntity<>(restaurante, headers);

        ResponseEntity<Restaurante> response = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            entity,
            Restaurante.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Restaurante createdRestaurante = response.getBody();
        assertNotNull(createdRestaurante);
        assertEquals("La Buena Mesa", createdRestaurante.getNombre());
        assertEquals("Calle Principal 123", createdRestaurante.getDireccion());
        assertEquals(menu, createdRestaurante.getMenu());

        // Obtener por ID
        ResponseEntity<Restaurante> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + createdRestaurante.getIdRestaurante(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Restaurante.class
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Restaurante retrievedRestaurante = getResponse.getBody();
        assertNotNull(retrievedRestaurante);
        assertEquals("La Buena Mesa", retrievedRestaurante.getNombre());
    }

    @Test
    void testActualizarRestaurante() {
        // Crear restaurante inicial
        Map<String, Double> menu = new HashMap<>();
        menu.put("Hamburguesa", 8.99);
        
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("El Rincón");
        restaurante.setDireccion("Avenida Central 456");
        restaurante.setMenu(menu);
        restaurante = restauranteRepository.save(restaurante);

        // Actualizar restaurante
        restaurante.setNombre("El Rincón Gourmet");
        restaurante.setDireccion("Avenida Central 789");
        menu.put("Pizza", 12.99);
        restaurante.setMenu(menu);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Restaurante> updateEntity = new HttpEntity<>(restaurante, headers);

        ResponseEntity<Restaurante> updateResponse = restTemplate.exchange(
            getBaseUrl() + "/" + restaurante.getIdRestaurante(),
            HttpMethod.PUT,
            updateEntity,
            Restaurante.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Restaurante updatedRestaurante = updateResponse.getBody();
        assertNotNull(updatedRestaurante);
        assertEquals("El Rincón Gourmet", updatedRestaurante.getNombre());
        assertEquals("Avenida Central 789", updatedRestaurante.getDireccion());
        assertEquals(menu, updatedRestaurante.getMenu());
    }

    @Test
    void testEliminarRestaurante() {
        // Crear restaurante
        Map<String, Double> menu = new HashMap<>();
        menu.put("Hamburguesa", 8.99);
        
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("El Rincón");
        restaurante.setDireccion("Avenida Central 456");
        restaurante.setMenu(menu);
        restaurante = restauranteRepository.save(restaurante);

        // Eliminar restaurante
        restTemplate.delete(getBaseUrl() + "/" + restaurante.getIdRestaurante());

        // Verificar que se eliminó
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Restaurante> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + restaurante.getIdRestaurante(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Restaurante.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    void testObtenerTodosLosRestaurantes() {
        // Crear dos restaurantes
        Map<String, Double> menu1 = new HashMap<>();
        menu1.put("Hamburguesa", 8.99);
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNombre("El Rincón");
        restaurante1.setDireccion("Avenida Central 456");
        restaurante1.setMenu(menu1);
        restauranteRepository.save(restaurante1);

        Map<String, Double> menu2 = new HashMap<>();
        menu2.put("Pizza", 12.99);
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNombre("La Buena Mesa");
        restaurante2.setDireccion("Calle Principal 123");
        restaurante2.setMenu(menu2);
        restauranteRepository.save(restaurante2);

        // Obtener todos los restaurantes
        ResponseEntity<Restaurante[]> response = restTemplate.getForEntity(
            getBaseUrl(),
            Restaurante[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Restaurante[] restaurantes = response.getBody();
        assertNotNull(restaurantes);
        assertEquals(2, restaurantes.length);
    }
} 