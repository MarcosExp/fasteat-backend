package com.fasteat.fasteat_api.repositories;

import com.fasteat.fasteat_api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
}
