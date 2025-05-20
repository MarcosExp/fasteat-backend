package com.fasteat.fasteat_api.dto;
/*
 * Clase que representa un objeto de transferencia de datos (DTO) para un pedido.
 * Se utiliza para transferir información entre la capa de presentación y la capa de negocio.
 *  Contiene los atributos id, nombre del usuario, nombre del restaurante, detalles del pedido, total y estado.
 */

public class PedidoDTO {
    private int id;
    private String usuarioNombre;
    private String restauranteNombre;
    private String detalles;
    private double total;
    private boolean estado;

    public PedidoDTO() {}

    public PedidoDTO(int id, String usuarioNombre, String restauranteNombre, String detalles, double total, boolean estado) {
        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.restauranteNombre = restauranteNombre;
        this.detalles = detalles;
        this.total = total;
        this.estado = estado;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getRestauranteNombre() {
        return restauranteNombre;
    }

    public void setRestauranteNombre(String restauranteNombre) {
        this.restauranteNombre = restauranteNombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
