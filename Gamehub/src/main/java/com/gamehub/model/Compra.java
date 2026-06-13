package com.gamehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "compras")
public class Compra {

    @Id
    private String id;

    private String correoJugador;

    private String juegoId;
    
    private Double precioPagado;
    
    private String tituloJuego;


    private LocalDateTime fechaCompra =
            LocalDateTime.now();
}