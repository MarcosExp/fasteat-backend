package com.fasteat.fasteat_api.repositories;

import com.fasteat.fasteat_api.model.Producto;
import com.fasteat.fasteat_api.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void testFindByRestauranteIdRestaurante() {
        // Crear y guardar un restaurante
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        entityManager.persist(restaurante);

        // Crear y guardar productos
        Producto producto1 = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        Producto producto2 = new Producto("Pizza", "Pizza margarita", 12.99, "Italiana", "url2", restaurante);
        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.flush();

        // Buscar productos por ID de restaurante
        List<Producto> productos = productoRepository.findByRestauranteIdRestaurante(restaurante.getIdRestaurante());

        // Verificar resultados
        assertThat(productos).hasSize(2);
        assertThat(productos).extracting(Producto::getNombre).containsExactlyInAnyOrder("Hamburguesa", "Pizza");
    }

    @Test
    public void testFindByRestauranteIdRestauranteAndDisponibleTrue() {
        // Crear y guardar un restaurante
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        entityManager.persist(restaurante);

        // Crear y guardar productos
        Producto producto1 = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        Producto producto2 = new Producto("Pizza", "Pizza margarita", 12.99, "Italiana", "url2", restaurante);
        producto2.setDisponible(false);
        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.flush();

        // Buscar productos disponibles por ID de restaurante
        List<Producto> productos = productoRepository.findByRestauranteIdRestauranteAndDisponibleTrue(restaurante.getIdRestaurante());

        // Verificar resultados
        assertThat(productos).hasSize(1);
        assertThat(productos.get(0).getNombre()).isEqualTo("Hamburguesa");
    }

    @Test
    public void testFindByRestauranteIdRestauranteAndCategoria() {
        // Crear y guardar un restaurante
        Restaurante restaurante = new Restaurante("Test Restaurant", "Test Address");
        entityManager.persist(restaurante);

        // Crear y guardar productos
        Producto producto1 = new Producto("Hamburguesa", "Deliciosa hamburguesa", 9.99, "Comida rápida", "url1", restaurante);
        Producto producto2 = new Producto("Pizza", "Pizza margarita", 12.99, "Italiana", "url2", restaurante);
        entityManager.persist(producto1);
        entityManager.persist(producto2);
        entityManager.flush();

        // Buscar productos por categoría
        List<Producto> productos = productoRepository.findByRestauranteIdRestauranteAndCategoria(restaurante.getIdRestaurante(), "Comida rápida");

        // Verificar resultados
        assertThat(productos).hasSize(1);
        assertThat(productos.get(0).getNombre()).isEqualTo("Hamburguesa");
    }
} 