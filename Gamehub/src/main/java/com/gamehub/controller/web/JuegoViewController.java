package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JuegoViewController {

    @GetMapping("/juegos")
    public String juegos() {

        return "juegos";
    }

    @GetMapping("/juego/{id}")
    public String detalleJuego(
            @PathVariable String id
    ) {

        return "detalle-juego";
    }
}