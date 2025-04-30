package com.fasteat.fasteat_api.model;

import jakarta.persistence.*;
import java.util.Objects;
/*
 * Clase que define la entidad Usuario
 * Define los atributos de un usuario
 */

@Entity
public class Usuario {

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.CLIENTE; // Asignar un rol por defecto de usuario
    public enum Rol {
        CLIENTE,
        ADMIN,
        OWNER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idUsuario;
    private String nombre;
    private String email;
    private String password;

    // Constructor, getters y setters

    public Usuario() {}

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }
}