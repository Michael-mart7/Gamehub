package com.gamehub.controller;

import com.gamehub.model.EstadisticasDTO;
import com.gamehub.service.EstadisticasService;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService service;

    public EstadisticasController(
            EstadisticasService service
    ){
        this.service = service;
    }

    @GetMapping
    public EstadisticasDTO obtener(
            Authentication auth
    ){

        return service.obtener(
                auth.getName()
        );
    }
}