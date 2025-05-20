package com.fasteat.fasteat_api.dto;

import java.util.Map;

public class RestauranteResponseDTO {
    private int idRestaurante;
    private String nombre;
    private String direccion;
    private Map<String, Double> menu;

    public RestauranteResponseDTO() {}

    public RestauranteResponseDTO(int idRestaurante, String nombre, String direccion, Map<String, Double> menu) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.direccion = direccion;
        this.menu = menu;
    }

    // Getters y setters
    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Map<String, Double> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Double> menu) {
        this.menu = menu;
    }
} 