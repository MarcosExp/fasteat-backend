package com.fasteat.fasteat_api.repositories;

import com.fasteat.fasteat_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByRestauranteIdRestaurante(int idRestaurante);
    List<Producto> findByRestauranteIdRestauranteAndDisponibleTrue(int idRestaurante);
    List<Producto> findByRestauranteIdRestauranteAndCategoria(int idRestaurante, String categoria);
} 