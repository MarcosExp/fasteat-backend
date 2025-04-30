package com.fasteat.fasteat_api.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.repositories.UsuarioRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void shouldSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        assertNotNull(usuarioRepository.save(usuario).getIdUsuario());
    }
}