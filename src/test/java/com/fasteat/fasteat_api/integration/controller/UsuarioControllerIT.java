package com.fasteat.fasteat_api.integration.controller;

import com.fasteat.fasteat_api.dto.CreateUsuarioRequest;
import com.fasteat.fasteat_api.dto.LoginRequest;
import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.repositories.UsuarioRepository;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/usuarios";
    }

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        usuarioRepository.deleteAll();
        restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Test
    void testCrearYObtenerUsuario() {
        CreateUsuarioRequest request = new CreateUsuarioRequest();
        request.setNombre("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUsuarioRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Usuario> response = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            entity,
            Usuario.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Usuario usuario = response.getBody();
        assertNotNull(usuario);
        assertEquals("Test User", usuario.getNombre());
        assertEquals("test@example.com", usuario.getEmail());
        assertNotNull(usuario.getIdUsuario());

        // Obtener por ID
        ResponseEntity<Usuario> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + usuario.getIdUsuario(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Usuario.class
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Usuario usuarioById = getResponse.getBody();
        assertNotNull(usuarioById);
        assertEquals("Test User", usuarioById.getNombre());
    }

    @Test
    void testActualizarUsuario() {
        // Crear usuario
        CreateUsuarioRequest request = new CreateUsuarioRequest();
        request.setNombre("Original");
        request.setEmail("original@example.com");
        request.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUsuarioRequest> createEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Usuario> createResponse = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            createEntity,
            Usuario.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Usuario usuario = createResponse.getBody();
        assertNotNull(usuario);

        // Actualizar usuario
        usuario.setNombre("Modificado");
        HttpEntity<Usuario> updateEntity = new HttpEntity<>(usuario, headers);
        ResponseEntity<Usuario> updateResponse = restTemplate.exchange(
            getBaseUrl() + "/" + usuario.getIdUsuario(),
            HttpMethod.PUT,
            updateEntity,
            Usuario.class
        );
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Usuario actualizado = updateResponse.getBody();
        assertNotNull(actualizado);
        assertEquals("Modificado", actualizado.getNombre());
    }

    @Test
    void testEliminarUsuario() {
        // Crear usuario
        CreateUsuarioRequest request = new CreateUsuarioRequest();
        request.setNombre("Eliminar");
        request.setEmail("eliminar@example.com");
        request.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUsuarioRequest> createEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Usuario> createResponse = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            createEntity,
            Usuario.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Usuario usuario = createResponse.getBody();
        assertNotNull(usuario);

        // Eliminar usuario
        restTemplate.delete(getBaseUrl() + "/" + usuario.getIdUsuario());

        // Verificar que se eliminó
        ResponseEntity<Usuario> getResponse = restTemplate.exchange(
            getBaseUrl() + "/" + usuario.getIdUsuario(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Usuario.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    void testLoginUsuario() {
        // Crear usuario
        CreateUsuarioRequest request = new CreateUsuarioRequest();
        request.setNombre("Login");
        request.setEmail("login@example.com");
        request.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUsuarioRequest> createEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Usuario> createResponse = restTemplate.exchange(
            getBaseUrl(),
            HttpMethod.POST,
            createEntity,
            Usuario.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        // Login correcto
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("login@example.com");
        loginRequest.setPassword("password123");
        HttpEntity<LoginRequest> loginEntity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<Usuario> loginResponse = restTemplate.exchange(
            getBaseUrl() + "/login",
            HttpMethod.POST,
            loginEntity,
            Usuario.class
        );
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

        // Login incorrecto
        loginRequest.setPassword("wrongpassword");
        loginEntity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<String> badResponse = restTemplate.exchange(
            getBaseUrl() + "/login",
            HttpMethod.POST,
            loginEntity,
            String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, badResponse.getStatusCode());
    }
} 