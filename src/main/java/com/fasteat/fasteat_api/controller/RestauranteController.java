package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Clase que define el controlador de restaurantes
 * Define los metodos para obtener, crear, actualizar y eliminar restaurantes   
 */

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<Restaurante> getAllRestaurantes() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getRestauranteById(@PathVariable int id) {
        return restauranteRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Restaurante createRestaurante(@RequestBody Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> updateRestaurante(@PathVariable int id, @RequestBody Restaurante restauranteDetails) {
        return restauranteRepository.findById(id)
            .map(restaurante -> {
                restaurante.setNombre(restauranteDetails.getNombre());
                restaurante.setDireccion(restauranteDetails.getDireccion());
                restaurante.setMenu(restauranteDetails.getMenu());
                return ResponseEntity.ok(restauranteRepository.save(restaurante));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable int id) {
        return restauranteRepository.findById(id)
            .map(restaurante -> {
                restauranteRepository.delete(restaurante);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}