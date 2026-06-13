package com.gamehub.controller;

import com.gamehub.model.Calificacion;
import com.gamehub.service.CalificacionService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService
            calificacionService;

    public CalificacionController(

            CalificacionService
                    calificacionService
    ){

        this.calificacionService =
                calificacionService;
    }

    // =====================
    // CALIFICAR
    // =====================

    @PostMapping("/{juegoId}")
    public Calificacion calificar(

            @PathVariable String juegoId,

            @RequestParam Integer puntuacion,

            Authentication auth
    ){

        return calificacionService
                .calificar(

                        auth.getName(),

                        juegoId,

                        puntuacion
                );
    }

    // =====================
    // PROMEDIO
    // =====================

    @GetMapping("/promedio/{juegoId}")
    public double promedio(

            @PathVariable String juegoId
    ){

        return calificacionService
                .promedioJuego(
                        juegoId
                );
    }

    // =====================
    // MI CALIFICACIÓN
    // =====================

    @GetMapping("/mia/{juegoId}")
    public Integer miCalificacion(

            @PathVariable String juegoId,

            Authentication auth
    ){

        return calificacionService
                .miCalificacion(

                        auth.getName(),

                        juegoId
                );
    }
}