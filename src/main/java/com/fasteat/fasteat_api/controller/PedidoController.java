package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Pedido;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Clase que define el controlador de pedidos
 * Define los metodos para obtener, crear, actualizar y eliminar pedidos
 */

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable int id) {
        return pedidoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable int id, @RequestBody Pedido pedidoDetails) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                pedido.setUsuario(pedidoDetails.getUsuario());
                pedido.setRestaurante(pedidoDetails.getRestaurante());
                pedido.setDetalles(pedidoDetails.getDetalles());
                pedido.setEstado(pedidoDetails.isEstado());
                pedido.setTotal(pedidoDetails.getTotal());
                return ResponseEntity.ok(pedidoRepository.save(pedido));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable int id) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                pedidoRepository.delete(pedido);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}