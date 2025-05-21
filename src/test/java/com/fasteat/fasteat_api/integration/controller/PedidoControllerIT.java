package com.fasteat.fasteat_api.integration.controller;

import com.fasteat.fasteat_api.model.Pedido;
import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import com.fasteat.fasteat_api.repositories.UsuarioRepository;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
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
public class PedidoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/pedidos";
    }

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        usuarioRepository.deleteAll();
        restauranteRepository.deleteAll();
    }

    @Test
    void testCrearYObtenerPedido() {
        // Crear usuario y restaurante necesarios
        Usuario usuario = new Usuario("Test User", "test@example.com", "password123");
        usuario = usuarioRepository.save(usuario);

        Map<Integer, Double> menu = new HashMap<>();
        menu.put(1, 8.99);
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address", menu);
        restaurante = restauranteRepository.save(restaurante);

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setDetalles("2 Hamburguesas");
        pedido.setEstado(true);
        pedido.setTotal(17.98);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Pedido> entity = new HttpEntity<>(pedido, headers);

        ResponseEntity<Pedido> response = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            entity,
            Pedido.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Pedido createdPedido = response.getBody();
        assertNotNull(createdPedido);
        assertEquals("2 Hamburguesas", createdPedido.getDetalles());
        assertEquals(17.98, createdPedido.getTotal());
        assertTrue(createdPedido.isEstado());

        // Obtener por ID
        ResponseEntity<Pedido> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + createdPedido.getIdPedido(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Pedido.class
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Pedido retrievedPedido = getResponse.getBody();
        assertNotNull(retrievedPedido);
        assertEquals("2 Hamburguesas", retrievedPedido.getDetalles());
    }

    @Test
    void testActualizarPedido() {
        // Crear usuario y restaurante
        Usuario usuario = new Usuario("Test User", "test@example.com", "password123");
        usuario = usuarioRepository.save(usuario);

        Map<Integer, Double> menu = new HashMap<>();
        menu.put(1, 8.99);
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address", menu);
        restaurante = restauranteRepository.save(restaurante);

        // Crear pedido inicial
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setDetalles("1 Hamburguesa");
        pedido.setEstado(true);
        pedido.setTotal(8.99);
        pedido = pedidoRepository.save(pedido);

        // Actualizar pedido
        pedido.setDetalles("3 Hamburguesas");
        pedido.setTotal(26.97);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Pedido> updateEntity = new HttpEntity<>(pedido, headers);

        ResponseEntity<Pedido> updateResponse = restTemplate.exchange(
            getBaseUrl() + "/" + pedido.getIdPedido(),
            HttpMethod.PUT,
            updateEntity,
            Pedido.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Pedido updatedPedido = updateResponse.getBody();
        assertNotNull(updatedPedido);
        assertEquals("3 Hamburguesas", updatedPedido.getDetalles());
        assertEquals(26.97, updatedPedido.getTotal());
    }

    @Test
    void testEliminarPedido() {
        // Crear usuario y restaurante
        Usuario usuario = new Usuario("Test User", "test@example.com", "password123");
        usuario = usuarioRepository.save(usuario);

        Map<Integer, Double> menu = new HashMap<>();
        menu.put(1, 8.99);
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address", menu);
        restaurante = restauranteRepository.save(restaurante);

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setDetalles("1 Hamburguesa");
        pedido.setEstado(true);
        pedido.setTotal(8.99);
        pedido = pedidoRepository.save(pedido);

        // Eliminar pedido
        restTemplate.delete(getBaseUrl() + "/" + pedido.getIdPedido());

        // Verificar que se eliminó
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Pedido> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + pedido.getIdPedido(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Pedido.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    void testObtenerTodosLosPedidos() {
        // Crear usuario y restaurante
        Usuario usuario = new Usuario("Test User", "test@example.com", "password123");
        usuario = usuarioRepository.save(usuario);

        Map<Integer, Double> menu = new HashMap<>();
        menu.put(1, 8.99);
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address", menu);
        restaurante = restauranteRepository.save(restaurante);

        // Crear dos pedidos
        Pedido pedido1 = new Pedido();
        pedido1.setUsuario(usuario);
        pedido1.setRestaurante(restaurante);
        pedido1.setDetalles("1 Hamburguesa");
        pedido1.setEstado(true);
        pedido1.setTotal(8.99);
        pedidoRepository.save(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setUsuario(usuario);
        pedido2.setRestaurante(restaurante);
        pedido2.setDetalles("2 Hamburguesas");
        pedido2.setEstado(true);
        pedido2.setTotal(17.98);
        pedidoRepository.save(pedido2);

        // Obtener todos los pedidos
        ResponseEntity<Pedido[]> response = restTemplate.getForEntity(
            getBaseUrl(),
            Pedido[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Pedido[] pedidos = response.getBody();
        assertNotNull(pedidos);
        assertEquals(2, pedidos.length);
    }
} 