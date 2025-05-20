package com.fasteat.fasteat_api.model;

import jakarta.persistence.*;
import com.fasteat.fasteat_api.converter.MenuConverter;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
/*
 * Clase que define la entidad Restaurante
 * Define los atributos de un restaurante
 */
@Entity
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRestaurante;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Convert(converter = MenuConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Double> menu = new HashMap<>();

    // Constructor, getters y setters
    public Restaurante() {
        this.menu = new HashMap<>();
    }

    public Restaurante(String nombre, String direccion, Map<String, Double> menu) {
        this.nombre = nombre;
        this.direccion = direccion;
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
        return menu != null ? menu : new HashMap<>();
    }

    public void setMenu(Map<String, Double> menu) {
        this.menu = menu != null ? menu : new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return idRestaurante == that.idRestaurante;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRestaurante);
    }
}