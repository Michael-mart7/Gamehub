package com.gamehub.service;

import com.gamehub.model.MensajeTicket;
import com.gamehub.model.TicketSoporte;
import com.gamehub.repository.TicketSoporteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketSoporteService {

    private final
    TicketSoporteRepository repo;

    public TicketSoporteService(

            TicketSoporteRepository repo
    ){

        this.repo = repo;
    }

    // =====================
    // CREAR TICKET
    // =====================

    public TicketSoporte crear(

            String correo,

            String asunto,

            String mensaje
    ){

        TicketSoporte ticket =
                new TicketSoporte();

        ticket.setCorreoUsuario(
                correo
        );

        ticket.setAsunto(
                asunto
        );

        // mensaje inicial del usuario
        ticket.getMensajes().add(

                new MensajeTicket(

                        correo,

                        mensaje,

                        null
                )
        );

        return repo.save(ticket);
    }

    // =====================
    // MIS TICKETS
    // =====================

    public List<TicketSoporte>
    misTickets(

            String correo
    ){

        return repo
                .findByCorreoUsuarioOrderByFechaDesc(
                        correo
                );
    }

    // =====================
    // TODOS LOS TICKETS
    // =====================

    public List<TicketSoporte>
    listarTodos(){

        return repo
                .findAllByOrderByFechaDesc();
    }

    // =====================
    // RESPONDER SOPORTE
    // =====================

    public TicketSoporte responder(

            String id,

            String respuesta
    ){

        TicketSoporte ticket =
                repo.findById(id)
                        .orElseThrow();

        ticket.getMensajes().add(

                new MensajeTicket(

                        "Soporte",

                        respuesta,

                        null
                )
        );

        if(ticket.getEstado()
                .equals("ABIERTO")){

            ticket.setEstado(
                    "EN_PROCESO"
            );
        }

        return repo.save(ticket);
    }

    // =====================
    // RESPONDER USUARIO
    // =====================

    public TicketSoporte responderUsuario(

            String id,

            String correo,

            String mensaje
    ){

        TicketSoporte ticket =
                repo.findById(id)
                        .orElseThrow();

        ticket.getMensajes().add(

                new MensajeTicket(

                        correo,

                        mensaje,

                        null
                )
        );

        return repo.save(ticket);
    }

    // =====================
    // CAMBIAR ESTADO
    // =====================

    public TicketSoporte cambiarEstado(

            String id,

            String estado
    ){

        TicketSoporte ticket =
                repo.findById(id)
                        .orElseThrow();

        ticket.setEstado(
                estado
        );

        return repo.save(ticket);
    }
}