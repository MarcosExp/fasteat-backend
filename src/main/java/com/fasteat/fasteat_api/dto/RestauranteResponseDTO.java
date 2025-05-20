package com.fasteat.fasteat_api.dto;

import java.util.Map;
import java.util.HashMap;

public class RestauranteResponseDTO {
    private int idRestaurante;
    private String nombre;
    private String direccion;
    private Map<String, Double> menu;

    public RestauranteResponseDTO() {
        this.menu = new HashMap<>();
    }

    public RestauranteResponseDTO(int idRestaurante, String nombre, String direccion, Map<String, Double> menu) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre != null ? nombre : "";
        this.direccion = direccion != null ? direccion : "";
        this.menu = menu != null ? menu : new HashMap<>();
    }

    // Getters y setters
    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre != null ? nombre : "";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre != null ? nombre : "";
    }

    public String getDireccion() {
        return direccion != null ? direccion : "";
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion != null ? direccion : "";
    }

    public Map<String, Double> getMenu() {
        return menu != null ? menu : new HashMap<>();
    }

    public void setMenu(Map<String, Double> menu) {
        this.menu = menu != null ? menu : new HashMap<>();
    }
} 