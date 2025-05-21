package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Producto;
import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.ProductoRepository;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import com.fasteat.fasteat_api.dto.ProductoDTO;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
@Tag(name = "Producto", description = "API para la gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private ProductoDTO convertToDTO(Producto producto) {
        return new ProductoDTO(
            producto.getIdProducto(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getCategoria(),
            producto.isDisponible(),
            producto.getUrlImagen(),
            producto.getRestaurante().getIdRestaurante()
        );
    }

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productosDTO = productos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productosDTO);
    }

    @Operation(summary = "Obtener todos los productos de un restaurante", description = "Retorna una lista de todos los productos de un restaurante específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<ProductoDTO>> getProductosByRestaurante(
            @Parameter(description = "ID del restaurante") @PathVariable int idRestaurante) {
        if (!restauranteRepository.existsById(idRestaurante)) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = productoRepository.findByRestauranteIdRestaurante(idRestaurante);
        List<ProductoDTO> productosDTO = productos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productosDTO);
    }

    @Operation(summary = "Obtener productos disponibles de un restaurante", description = "Retorna una lista de productos disponibles de un restaurante específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @GetMapping("/restaurante/{idRestaurante}/disponibles")
    public ResponseEntity<List<ProductoDTO>> getProductosDisponiblesByRestaurante(
            @Parameter(description = "ID del restaurante") @PathVariable int idRestaurante) {
        if (!restauranteRepository.existsById(idRestaurante)) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = productoRepository.findByRestauranteIdRestauranteAndDisponibleTrue(idRestaurante);
        List<ProductoDTO> productosDTO = productos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productosDTO);
    }

    @Operation(summary = "Obtener productos por categoría", description = "Retorna una lista de productos de una categoría específica de un restaurante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @GetMapping("/restaurante/{idRestaurante}/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(
            @Parameter(description = "ID del restaurante") @PathVariable int idRestaurante,
            @Parameter(description = "Categoría de los productos") @PathVariable String categoria) {
        if (!restauranteRepository.existsById(idRestaurante)) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = productoRepository.findByRestauranteIdRestauranteAndCategoria(idRestaurante, categoria);
        List<ProductoDTO> productosDTO = productos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productosDTO);
    }

    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto para un restaurante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @PostMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<ProductoDTO> createProducto(
            @Parameter(description = "ID del restaurante") @PathVariable int idRestaurante,
            @Parameter(description = "Datos del producto") @RequestBody Producto producto) {
        return restauranteRepository.findById(idRestaurante)
            .<ResponseEntity<ProductoDTO>>map(restaurante -> {
                // Validar datos requeridos
                if (producto.getNombre() == null || producto.getNombre().trim().isEmpty() ||
                    producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty() ||
                    producto.getPrecio() <= 0 ||
                    producto.getCategoria() == null || producto.getCategoria().trim().isEmpty() ||
                    producto.getUrlImagen() == null || producto.getUrlImagen().trim().isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }

                producto.setRestaurante(restaurante);
                producto.setDisponible(true);
                
                Producto savedProducto = productoRepository.save(producto);
                
                Map<Integer, Double> menu = restaurante.getMenu();
                menu.put(savedProducto.getIdProducto(), savedProducto.getPrecio());
                restaurante.setMenu(menu);
                restauranteRepository.save(restaurante);
                
                return ResponseEntity.ok(convertToDTO(savedProducto));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(
            @Parameter(description = "ID del producto") @PathVariable int id,
            @Parameter(description = "Nuevos datos del producto") @RequestBody Producto productoDetails) {
        return productoRepository.findById(id)
            .map(producto -> {
                producto.setNombre(productoDetails.getNombre());
                producto.setDescripcion(productoDetails.getDescripcion());
                producto.setPrecio(productoDetails.getPrecio());
                producto.setCategoria(productoDetails.getCategoria());
                producto.setDisponible(productoDetails.isDisponible());
                producto.setUrlImagen(productoDetails.getUrlImagen());
                
                Producto updatedProducto = productoRepository.save(producto);
                
                Restaurante restaurante = producto.getRestaurante();
                if (restaurante != null) {
                    Map<Integer, Double> menu = restaurante.getMenu();
                    menu.put(updatedProducto.getIdProducto(), updatedProducto.getPrecio());
                    restaurante.setMenu(menu);
                    restauranteRepository.save(restaurante);
                }
                
                return ResponseEntity.ok(convertToDTO(updatedProducto));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(
            @Parameter(description = "ID del producto") @PathVariable int id) {
        return productoRepository.findById(id)
            .map(producto -> {
                Restaurante restaurante = producto.getRestaurante();
                if (restaurante != null) {
                    restaurante.removeProducto(producto);
                    restauranteRepository.save(restaurante);
                }
                productoRepository.delete(producto);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 