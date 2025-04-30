package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Pedido;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Pedido getPedidoById(@PathVariable int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido updatePedido(@PathVariable int id, @RequestBody Pedido pedidoDetails) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setUsuario(pedidoDetails.getUsuario());
            pedido.setRestaurante(pedidoDetails.getRestaurante());
            pedido.setDetalles(pedidoDetails.getDetalles());
            pedido.setEstado(pedidoDetails.isEstado());
            pedido.setTotal(pedidoDetails.getTotal());
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable int id) {
        pedidoRepository.deleteById(id);
    }
}