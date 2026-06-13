package com.gamehub.model;

import lombok.Data;

@Data
public class EstadisticasDTO {

    private long usuarios;

    private long videojuegos;

    private long compras;

    private long comentarios;

    private long reportes;

    private long tickets;

    private long noticias;

    private String juegoMasComprado;

    private long comprasHoy;

    private long comprasMes;
    
    private String rol;
    private Double ingresos;
}