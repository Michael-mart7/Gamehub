package com.gamehub.repository;

import com.gamehub.model.Juego;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository
        extends MongoRepository<Juego, String> {

    // Buscar por título
    List<Juego> findByTituloContainingIgnoreCase(
            String titulo
    );

    // Buscar por género
    List<Juego> findByGeneroIgnoreCase(
            String genero
    );

    // Solo activos
    List<Juego> findByActivoTrue();
    
    List<Juego> findByDesarrollador(
            String desarrollador
    );

    // Juegos destacados
    List<Juego> findByDestacadoTrue();

    // Buscar por rango de precio
    List<Juego> findByPrecioBetween(
            Double min,
            Double max
    );
}