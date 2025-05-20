package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Pedido;
import com.fasteat.fasteat_api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/*
 * Clase que define el controlador de pedidos
 * Define los metodos para obtener, crear, actualizar y eliminar pedidos
 */

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedido", description = "API para la gestión de pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista de todos los pedidos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos encontrada exitosamente",
        content = @Content(schema = @Schema(implementation = Pedido.class)))
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(
            @Parameter(description = "ID del pedido a buscar") @PathVariable int id) {
        return pedidoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente",
            content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    @PostMapping
    public Pedido createPedido(
            @Parameter(description = "Datos del pedido a crear") @RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos de un pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(
            @Parameter(description = "ID del pedido a actualizar") @PathVariable int id,
            @Parameter(description = "Nuevos datos del pedido") @RequestBody Pedido pedidoDetails) {
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

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(
            @Parameter(description = "ID del pedido a eliminar") @PathVariable int id) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                pedidoRepository.delete(pedido);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}