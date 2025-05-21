package com.fasteat.fasteat_api.model;

import jakarta.persistence.*;
import com.fasteat.fasteat_api.converter.MenuConverter;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/*
 * Clase que define la entidad Restaurante
 * Define los atributos de un restaurante
 */
@Entity
@Table(name = "restaurante")
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
    private Map<Integer, Double> menu = new HashMap<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    // Constructor, getters y setters
    public Restaurante() {
        this.menu = new HashMap<>();
        this.productos = new ArrayList<>();
    }

    public Restaurante(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.menu = new HashMap<>();
        this.productos = new ArrayList<>();
    }

    public Restaurante(String nombre, String direccion, Map<Integer, Double> menu) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.menu = menu != null ? menu : new HashMap<>();
        this.productos = new ArrayList<>();
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

    public Map<Integer, Double> getMenu() {
        return menu != null ? menu : new HashMap<>();
    }

    public void setMenu(Map<Integer, Double> menu) {
        this.menu = menu != null ? menu : new HashMap<>();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto) {
        productos.add(producto);
        producto.setRestaurante(this);
        // Actualizar el menú JSON con el nuevo producto usando su ID
        menu.put(producto.getIdProducto(), producto.getPrecio());
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setRestaurante(null);
        // Eliminar el producto del menú JSON usando su ID
        menu.remove(producto.getIdProducto());
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