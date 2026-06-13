package com.gamehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tickets_soporte")
public class TicketSoporte {

    @Id
    private String id;

    // usuario creador
    private String correoUsuario;

    // asunto del ticket
    private String asunto;

    // conversación del ticket
    private List<MensajeTicket> mensajes =
            new ArrayList<>();

    // estado
    private String estado =
            "ABIERTO";

    // fecha creación
    private LocalDateTime fecha =
            LocalDateTime.now();
}