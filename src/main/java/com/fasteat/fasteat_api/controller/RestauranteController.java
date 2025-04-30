package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.model.Restaurante;
import com.fasteat.fasteat_api.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Restaurante getRestauranteById(@PathVariable int id) {
        return restauranteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Restaurante createRestaurante(@RequestBody Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @PutMapping("/{id}")
    public Restaurante updateRestaurante(@PathVariable int id, @RequestBody Restaurante restauranteDetails) {
        Restaurante restaurante = restauranteRepository.findById(id).orElse(null);
        if (restaurante != null) {
            restaurante.setNombre(restauranteDetails.getNombre());
            restaurante.setDireccion(restauranteDetails.getDireccion());
            restaurante.setMenu(restauranteDetails.getMenu());
            return restauranteRepository.save(restaurante);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurante(@PathVariable int id) {
        restauranteRepository.deleteById(id);
    }
}