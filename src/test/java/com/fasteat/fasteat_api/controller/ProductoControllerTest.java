package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Producto;
import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.ProductoRepository;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private RestauranteRepository restauranteRepository;

    @Test
    public void testGetProductosByRestaurante() throws Exception {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        Producto producto1 = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        Producto producto2 = new Producto("Pizza", "Pizza margarita", 12.99, "Italiana", "url2", restaurante);
        List<Producto> productos = Arrays.asList(producto1, producto2);

        when(restauranteRepository.existsById(1)).thenReturn(true);
        when(productoRepository.findByRestauranteIdRestaurante(1)).thenReturn(productos);

        mockMvc.perform(get("/productos/restaurante/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Hamburguesa"))
                .andExpect(jsonPath("$[1].nombre").value("Pizza"));
    }

    @Test
    public void testGetProductosDisponiblesByRestaurante() throws Exception {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        Producto producto = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        List<Producto> productos = Arrays.asList(producto);

        when(restauranteRepository.existsById(1)).thenReturn(true);
        when(productoRepository.findByRestauranteIdRestauranteAndDisponibleTrue(1)).thenReturn(productos);

        mockMvc.perform(get("/productos/restaurante/1/disponibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Hamburguesa"));
    }

    @Test
    public void testCreateProducto() throws Exception {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        restaurante.setIdRestaurante(1);

        Producto producto = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", null);
        producto.setIdProducto(1);

        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        String productoJson = "{" +
                "\"nombre\": \"Hamburguesa\"," +
                "\"descripcion\": \"Deliciosa hamburguesa\"," +
                "\"precio\": 9.99," +
                "\"categoria\": \"Comida rápida\"," +
                "\"urlImagen\": \"url1\"," +
                "\"disponible\": true" +
                "}";

        mockMvc.perform(post("/productos/restaurante/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Hamburguesa"))
                .andExpect(jsonPath("$.descripcion").value("Deliciosa hamburguesa"))
                .andExpect(jsonPath("$.precio").value(9.99))
                .andExpect(jsonPath("$.categoria").value("Comida rápida"));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        Producto producto = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        producto.setIdProducto(1);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Hamburguesa"));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        Producto producto = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        producto.setIdProducto(1);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isOk());
    }
} 