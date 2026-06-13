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
@Document(collection = "comentarios")
public class Comentario {

    @Id
    private String id;

    private String correoJugador;

    private String juegoId;

    private String comentario;

    private LocalDateTime fecha =
            LocalDateTime.now();
}