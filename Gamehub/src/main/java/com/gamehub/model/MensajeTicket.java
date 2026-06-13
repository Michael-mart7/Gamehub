package com.gamehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeTicket {

    private String autor;

    private String mensaje;

    private LocalDateTime fecha =
            LocalDateTime.now();
}