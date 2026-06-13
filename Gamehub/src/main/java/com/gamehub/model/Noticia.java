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
@Document(collection = "noticias")
public class Noticia {

    @Id
    private String id;

    private String titulo;

    private String contenido;

    // URL imagen
    private String imagen;

    // NOTICIA o PROMOCION
    private String tipo;

    // correo editor
    private String autor;

    private boolean activa = true;

    private LocalDateTime fecha =
            LocalDateTime.now();
}