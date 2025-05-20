package com.fasteat.fasteat_api.dto;

import com.fasteat.fasteat_api.model.Usuario;
/*
 * Clase que representa un objeto de transferencia de datos (DTO) para un usuario.
 * Se utiliza para transferir información entre la capa de presentación y la capa de negocio.
 * Contiene los atributos id, nombre, email y rol del usuario.
 */

public class UsuarioDTO {
    private int id;
    private String nombre;
    private String email;
    private Usuario.Rol rol;

    public UsuarioDTO() {}

    public UsuarioDTO(int id, String nombre, String email, Usuario.Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Usuario.Rol getRol() {
        return rol;
    }

    public void setRol(Usuario.Rol rol) {
        this.rol = rol;
    }
}
