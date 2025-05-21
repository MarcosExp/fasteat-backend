package com.fasteat.fasteat_api.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void testCrearProducto() {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        Producto producto = new Producto(
            "Hamburguesa",
            "Deliciosa hamburguesa con queso",
            9.99,
            "Comida rápida",
            "http://ejemplo.com/imagen.jpg",
            restaurante
        );

        assertEquals("Hamburguesa", producto.getNombre());
        assertEquals("Deliciosa hamburguesa con queso", producto.getDescripcion());
        assertEquals(9.99, producto.getPrecio());
        assertEquals("Comida rápida", producto.getCategoria());
        assertEquals("http://ejemplo.com/imagen.jpg", producto.getUrlImagen());
        assertTrue(producto.isDisponible());
        assertEquals(restaurante, producto.getRestaurante());
    }

    @Test
    public void testSettersAndGetters() {
        Producto producto = new Producto();
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");

        producto.setIdProducto(1);
        producto.setNombre("Pizza");
        producto.setDescripcion("Pizza margarita");
        producto.setPrecio(12.99);
        producto.setCategoria("Italiana");
        producto.setDisponible(false);
        producto.setUrlImagen("http://ejemplo.com/pizza.jpg");
        producto.setRestaurante(restaurante);

        assertEquals(1, producto.getIdProducto());
        assertEquals("Pizza", producto.getNombre());
        assertEquals("Pizza margarita", producto.getDescripcion());
        assertEquals(12.99, producto.getPrecio());
        assertEquals("Italiana", producto.getCategoria());
        assertFalse(producto.isDisponible());
        assertEquals("http://ejemplo.com/pizza.jpg", producto.getUrlImagen());
        assertEquals(restaurante, producto.getRestaurante());
    }

    @Test
    public void testEqualsAndHashCode() {
        Producto producto1 = new Producto();
        producto1.setIdProducto(1);

        Producto producto2 = new Producto();
        producto2.setIdProducto(1);

        Producto producto3 = new Producto();
        producto3.setIdProducto(2);

        assertTrue(producto1.equals(producto2));
        assertFalse(producto1.equals(producto3));
        assertEquals(producto1.hashCode(), producto2.hashCode());
        assertNotEquals(producto1.hashCode(), producto3.hashCode());
    }
} 