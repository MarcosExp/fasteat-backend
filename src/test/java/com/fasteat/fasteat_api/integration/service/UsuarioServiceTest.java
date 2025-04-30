package com.fasteat.fasteat_api.integration.service;

import org.junit.jupiter.api.Test;

import com.fasteat.fasteat_api.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {
    
    private UsuarioService usuarioService = new UsuarioService();

    @Test
    public void testPasswordEncoding() {
        String rawPassword = "password123";
        String encodedPassword = usuarioService.encodePassword(rawPassword);
        
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    public void testPasswordVerification() {
        String rawPassword = "password123";
        String encodedPassword = usuarioService.encodePassword(rawPassword);
        
        assertTrue(usuarioService.verifyPassword(rawPassword, encodedPassword));
        assertFalse(usuarioService.verifyPassword("wrongPassword", encodedPassword));
    }

    @Test
    public void testConsistentHashing() {
        String password = "password123";
        String firstHash = usuarioService.encodePassword(password);
        String secondHash = usuarioService.encodePassword(password);
        
        assertEquals(firstHash, secondHash);
    }
}