package com.fasteat.fasteat_api.unit.model;

import com.fasteat.fasteat_api.model.Restaurante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class RestauranteTest {
    
    @Test
    public void testRestauranteConstructorCompleto() {
        Map<String, Double> menu = new HashMap<>();
        menu.put("Hamburguesa", 8.99);
        menu.put("Pizza", 12.99);
        
        Restaurante restaurante = new Restaurante("La Buena Mesa", "Calle Principal 123", menu);
        assertEquals("La Buena Mesa", restaurante.getNombre());
        assertEquals("Calle Principal 123", restaurante.getDireccion());
        assertEquals(menu, restaurante.getMenu());
    }

    @Test
    public void testSettersAndGetters() {
        Restaurante restaurante = new Restaurante();
        restaurante.setIdRestaurante(1);
        restaurante.setNombre("El Rincón");
        restaurante.setDireccion("Avenida Central 456");
        
        Map<String, Double> menu = new HashMap<>();
        menu.put("Pasta", 9.99);
        restaurante.setMenu(menu);

        assertEquals(1, restaurante.getIdRestaurante());
        assertEquals("El Rincón", restaurante.getNombre());
        assertEquals("Avenida Central 456", restaurante.getDireccion());
        assertEquals(menu, restaurante.getMenu());
    }

    @Test
    public void testEquals() {
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setIdRestaurante(1);
        
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setIdRestaurante(1);
        
        assertEquals(restaurante1, restaurante2);
    }

    @Test
    public void testNotEquals() {
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setIdRestaurante(1);
        
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setIdRestaurante(2);
        
        assertNotEquals(restaurante1, restaurante2);
    }

    @Test
    public void testHashCode() {
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setIdRestaurante(1);
        
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setIdRestaurante(1);
        
        assertEquals(restaurante1.hashCode(), restaurante2.hashCode());
    }
} 