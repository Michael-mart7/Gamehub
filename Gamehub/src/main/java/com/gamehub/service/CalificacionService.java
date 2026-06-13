package com.gamehub.service;

import com.gamehub.model.Calificacion;
import com.gamehub.repository.CalificacionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionService {

    private final CalificacionRepository repo;

    private final CompraService compraService;

    public CalificacionService(

            CalificacionRepository repo,

            CompraService compraService
    ){

        this.repo = repo;

        this.compraService =
                compraService;
    }

    // =====================
    // CALIFICAR
    // =====================

    public Calificacion calificar(

            String correoJugador,

            String juegoId,

            Integer puntuacion
    ){

        // validar compra

        boolean comprado =
                compraService
                        .yaComprado(

                                correoJugador,

                                juegoId
                        );

        if(!comprado){

            throw new RuntimeException(

                    "Debes comprar el juego primero"
            );
        }

        var existente =

                repo.findByCorreoJugadorAndJuegoId(

                        correoJugador,

                        juegoId
                );

        Calificacion calificacion;

        // actualizar si ya existe

        if(existente.isPresent()){

            calificacion =
                    existente.get();

        }else{

            calificacion =
                    new Calificacion();

            calificacion.setCorreoJugador(
                    correoJugador
            );

            calificacion.setJuegoId(
                    juegoId
            );
        }

        calificacion.setPuntuacion(
                puntuacion
        );

        return repo.save(
                calificacion
        );
    }

    // =====================
    // PROMEDIO
    // =====================

    public double promedioJuego(
            String juegoId
    ){

        List<Calificacion>
                calificaciones =

                repo.findByJuegoId(
                        juegoId
                );

        if(calificaciones.isEmpty()){

            return 0;
        }

        return calificaciones.stream()

                .mapToInt(
                        Calificacion
                                ::getPuntuacion
                )

                .average()

                .orElse(0);
    }

    // =====================
    // MI CALIFICACIÓN
    // =====================

    public Integer
    miCalificacion(

            String correoJugador,

            String juegoId
    ){

        return repo

                .findByCorreoJugadorAndJuegoId(

                        correoJugador,

                        juegoId
                )

                .map(
                        Calificacion
                                ::getPuntuacion
                )

                .orElse(0);
    }
}