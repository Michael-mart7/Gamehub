package com.gamehub.controller;

import com.gamehub.model.Comentario;
import com.gamehub.service.ComentarioService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService
            comentarioService;

    public ComentarioController(

            ComentarioService
                    comentarioService
    ){

        this.comentarioService =
                comentarioService;
    }

    // =====================
    // COMENTAR
    // =====================

    @PostMapping("/{juegoId}")
    public Comentario comentar(

            @PathVariable String juegoId,

            @RequestParam String texto,

            Authentication auth
    ){

        return comentarioService
                .comentar(

                        auth.getName(),

                        juegoId,

                        texto
                );
    }

    // =====================
    // LISTAR
    // =====================

    @GetMapping("/{juegoId}")
    public List<Comentario>
    listarComentarios(

            @PathVariable String juegoId
    ){

        return comentarioService
                .listarComentarios(
                        juegoId
                );
    }
}