package com.gamehub.repository;

import com.gamehub.model.Compra;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository
        extends MongoRepository<Compra, String> {

    List<Compra> findByCorreoJugador(
            String correoJugador
    );

    Optional<Compra>
    findByCorreoJugadorAndJuegoId(
            String correoJugador,
            String juegoId
    );
}