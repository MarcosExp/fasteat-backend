package com.fasteat.fasteat_api.dto;

public class RestauranteResponseDTO {
    private int idRestaurante;
    private String nombre;
    private String direccion;
    private String menu;

    public RestauranteResponseDTO() {
        this.menu = "{}";
    }

    public RestauranteResponseDTO(int idRestaurante, String nombre, String direccion, String menu) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre != null ? nombre : "";
        this.direccion = direccion != null ? direccion : "";
        this.menu = menu != null ? menu : "{}";
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

    public String getMenu() {
        return menu != null ? menu : "{}";
    }

    public void setMenu(String menu) {
        this.menu = menu != null ? menu : "{}";
    }
} 