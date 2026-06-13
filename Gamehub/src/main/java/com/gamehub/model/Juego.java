package com.gamehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "juegos")
public class Juego {

    @Id
    private String id;

    // Nombre del videojuego
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    // Descripción
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    // Género principal
    @NotBlank(message = "El género es obligatorio")
    private String genero;

    // Etiquetas
    private List<String> etiquetas;

    // Precio
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
    private Double precioOriginal;

    // Imagen portada
    private String portada;

    // Desarrollador/publicador
    private String desarrollador;

    // Stock (por si luego quieres compras)
    private Integer stock = 999;

    // Juego activo o suspendido
    private boolean activo = true;

    // Destacado en landing
    private boolean destacado = false;

    // Descuento simple
    private Double descuento = 0.0;

    // Fecha de creación
    private LocalDateTime fechaRegistro =
            LocalDateTime.now();
}