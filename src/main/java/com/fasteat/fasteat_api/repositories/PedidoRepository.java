package com.fasteat.fasteat_api.repositories;

import com.fasteat.fasteat_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}