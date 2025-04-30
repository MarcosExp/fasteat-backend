package com.fasteat.fasteat_api.unit.model;

import com.fasteat.fasteat_api.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    
    @Test
    public void testUsuarioConstructorCompleto() {
        Usuario usuario = new Usuario("John Doe", "john.doe@example.com", "password123");
        assertEquals("John Doe", usuario.getNombre());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertEquals(Usuario.Rol.CLIENTE, usuario.getRol());
    }

    @Test
    public void testSettersAndGetters() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Jane Doe");
        usuario.setEmail("jane.doe@example.com");
        usuario.setIdUsuario(1);
        usuario.setPassword("password123");
        usuario.setRol(Usuario.Rol.ADMIN);

        assertEquals("Jane Doe", usuario.getNombre());
        assertEquals("jane.doe@example.com", usuario.getEmail());
        assertEquals(1, usuario.getIdUsuario());
        assertEquals("password123", usuario.getPassword());
        assertEquals(Usuario.Rol.ADMIN, usuario.getRol());
    }

    @Test
    public void testRolPorDefecto() {
        Usuario usuario = new Usuario();
        assertEquals(Usuario.Rol.CLIENTE, usuario.getRol());
    }

    @Test
    public void testCambioRol() {
        Usuario usuario = new Usuario();
        usuario.setRol(Usuario.Rol.OWNER);
        assertEquals(Usuario.Rol.OWNER, usuario.getRol());
    }

    @Test
    public void testEquals() {
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        
        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(1);
        
        assertEquals(usuario1, usuario2);
    }

    @Test
    public void testNotEquals() {
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        
        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        
        assertNotEquals(usuario1, usuario2);
    }

    @Test
    public void testHashCode() {
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        
        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(1);
        
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}