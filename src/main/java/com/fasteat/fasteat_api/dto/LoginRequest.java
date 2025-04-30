package com.fasteat.fasteat_api.dto;

/**
 * Clase que define la estructura de la solicitud de inicio de sesión.
 * Contiene los atributos necesarios para autenticar a un usuario.
 * Incluye el correo electrónico y la contraseña del usuario.
 */

public class LoginRequest {
    private String email;
    private String password;

    // Getters y setters
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
}