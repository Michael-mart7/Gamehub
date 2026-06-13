package com.gamehub.repository;

import com.gamehub.model.Calificacion;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository
        extends MongoRepository<Calificacion, String> {

    // Todas las calificaciones de un juego
    List<Calificacion>
    findByJuegoId(
            String juegoId
    );

    // Calificación del jugador sobre un juego
    Optional<Calificacion>
    findByCorreoJugadorAndJuegoId(

            String correoJugador,

            String juegoId
    );
}