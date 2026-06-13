package com.gamehub.controller;

import com.gamehub.model.TicketSoporte;
import com.gamehub.service.TicketSoporteService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final
    TicketSoporteService service;

    public TicketController(

            TicketSoporteService service
    ){

        this.service = service;
    }

    // =====================
    // CREAR TICKET
    // =====================

    @PostMapping
    public TicketSoporte crear(

            @RequestParam String asunto,

            @RequestParam String mensaje,

            Authentication auth
    ){

        return service.crear(

                auth.getName(),

                asunto,

                mensaje
        );
    }

    // =====================
    // MIS TICKETS
    // =====================

    @GetMapping("/mis")
    public List<TicketSoporte>
    misTickets(

            Authentication auth
    ){

        return service.misTickets(

                auth.getName()
        );
    }

    // =====================
    // TODOS (SOPORTE)
    // =====================

    @GetMapping
    public List<TicketSoporte>
    listar(){

        return service.listarTodos();
    }

    // =====================
    // RESPUESTA SOPORTE
    // =====================

    @PutMapping("/responder/{id}")
    public TicketSoporte responder(

            @PathVariable String id,

            @RequestParam String respuesta
    ){

        return service.responder(

                id,

                respuesta
        );
    }

    // =====================
    // RESPUESTA USUARIO
    // =====================

    @PutMapping("/usuario/{id}")
    public TicketSoporte responderUsuario(

            @PathVariable String id,

            @RequestParam String mensaje,

            Authentication auth
    ){

        return service.responderUsuario(

                id,

                auth.getName(),

                mensaje
        );
    }

    // =====================
    // CAMBIAR ESTADO
    // =====================

    @PutMapping("/estado/{id}")
    public TicketSoporte cambiarEstado(

            @PathVariable String id,

            @RequestParam String estado
    ){

        return service.cambiarEstado(

                id,

                estado
        );
    }
}