package com.fasteat.fasteat_api.unit.model;

import com.fasteat.fasteat_api.model.Pedido;
import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.model.Restaurante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class PedidoTest {
    
    @Test
    public void testPedidoConstructorCompleto() {
        Usuario usuario = new Usuario("John Doe", "john@example.com", "password");
        Map<String, Double> menu = new HashMap<>();
        menu.put("Hamburguesa", 8.99);
        Restaurante restaurante = new Restaurante("La Buena Mesa", "Calle Principal 123", menu);
        
        Pedido pedido = new Pedido(usuario, restaurante, "2 Hamburguesas", true, 17.98);
        
        assertEquals(usuario, pedido.getUsuario());
        assertEquals(restaurante, pedido.getRestaurante());
        assertEquals("2 Hamburguesas", pedido.getDetalles());
        assertTrue(pedido.isEstado());
        assertEquals(17.98, pedido.getTotal());
    }

    @Test
    public void testSettersAndGetters() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        
        Usuario usuario = new Usuario("Jane Doe", "jane@example.com", "password");
        pedido.setUsuario(usuario);
        
        Map<String, Double> menu = new HashMap<>();
        menu.put("Pizza", 12.99);
        Restaurante restaurante = new Restaurante("El Rincón", "Avenida Central 456", menu);
        pedido.setRestaurante(restaurante);
        
        pedido.setDetalles("1 Pizza");
        pedido.setEstado(false);
        pedido.setTotal(12.99);

        assertEquals(1, pedido.getIdPedido());
        assertEquals(usuario, pedido.getUsuario());
        assertEquals(restaurante, pedido.getRestaurante());
        assertEquals("1 Pizza", pedido.getDetalles());
        assertFalse(pedido.isEstado());
        assertEquals(12.99, pedido.getTotal());
    }

    @Test
    public void testEquals() {
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1);
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(1);
        
        assertEquals(pedido1, pedido2);
    }

    @Test
    public void testNotEquals() {
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1);
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2);
        
        assertNotEquals(pedido1, pedido2);
    }

    @Test
    public void testHashCode() {
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1);
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(1);
        
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }
} 