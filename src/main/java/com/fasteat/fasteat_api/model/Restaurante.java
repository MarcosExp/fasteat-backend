package com.fasteat.fasteat_api.model;

import jakarta.persistence.*;
import com.fasteat.fasteat_api.converter.MenuConverter;
import java.util.Map;
/*
 * Clase que define la entidad Restaurante
 * Define los atributos de un restaurante
 */
@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRestaurante;

    private String nombre;
    private String direccion;

    @Convert(converter = MenuConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Double> menu;

    // Constructor, getters y setters
    public Restaurante() {}

    public Restaurante(String nombre, String direccion, Map<String, Double> menu) {
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