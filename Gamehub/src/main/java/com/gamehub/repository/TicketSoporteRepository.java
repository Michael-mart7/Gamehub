package com.gamehub.repository;

import com.gamehub.model.TicketSoporte;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketSoporteRepository
        extends MongoRepository<
        TicketSoporte,
        String> {

    List<TicketSoporte>
    findByCorreoUsuarioOrderByFechaDesc(
            String correoUsuario
    );

    List<TicketSoporte>
    findAllByOrderByFechaDesc();
}