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
@Document(collection = "reportes_comentarios")
public class ReporteComentario {

    @Id
    private String id;

    // comentario reportado
    private String comentarioId;

    // texto del comentario
    private String comentarioTexto;

    // usuario que reporta
    private String correoReportante;

    // motivo del reporte
    private String motivo;

    // revisado por moderador
    private boolean revisado = false;

    // fecha
    private LocalDateTime fecha =
            LocalDateTime.now();
}