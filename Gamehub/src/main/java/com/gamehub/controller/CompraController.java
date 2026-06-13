package com.gamehub.controller;

import com.gamehub.model.Compra;
import com.gamehub.service.CompraService;
import com.gamehub.model.Juego;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(
            CompraService compraService
    ){
        this.compraService =
                compraService;
    }

    // Comprar videojuego
    @PostMapping("/{juegoId}")
    public Compra comprarJuego(

            @PathVariable String juegoId,

            Authentication auth
    ){

        return compraService.comprar(

                auth.getName(),
                juegoId
        );
    }

    // Obtener biblioteca del jugador
    @GetMapping
    public List<Compra> miBiblioteca(

            Authentication auth
    ){

        return compraService
                .obtenerBiblioteca(

                        auth.getName()
                );
    }
 // Biblioteca completa
    @GetMapping("/juegos")
    public List<Juego> juegosBiblioteca(

            Authentication auth
    ){

        return compraService
                .obtenerJuegosBiblioteca(

                        auth.getName()
                );
    }

    // Validar si ya compró un juego
    @GetMapping("/validar/{juegoId}")
    public boolean validarCompra(

            @PathVariable String juegoId,

            Authentication auth
    ){

        return compraService
                .yaComprado(

                        auth.getName(),
                        juegoId
                );
    }
    
    @GetMapping("/historial")
    public List<Compra> historial(

            Authentication auth
    ){

        return compraService.obtenerBiblioteca(
                auth.getName()
        );
    }
}