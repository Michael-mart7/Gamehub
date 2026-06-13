package com.gamehub.controller;

import com.gamehub.model.Comentario;
import com.gamehub.model.ReporteComentario;
import com.gamehub.repository.ComentarioRepository;
import com.gamehub.service.ReporteComentarioService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteComentarioController {

    private final ReporteComentarioService service;

    private final ComentarioRepository comentarioRepo;

    public ReporteComentarioController(

            ReporteComentarioService service,

            ComentarioRepository comentarioRepo
    ){

        this.service = service;

        this.comentarioRepo =
                comentarioRepo;
    }

    // =====================
    // REPORTAR COMENTARIO
    // =====================

    @PostMapping("/{comentarioId}")
    public ReporteComentario reportar(

            @PathVariable String comentarioId,

            @RequestParam String motivo,

            Authentication auth
    ){

        Comentario comentario =
                comentarioRepo
                        .findById(comentarioId)
                        .orElseThrow();

        return service.reportar(

                comentarioId,

                auth.getName(),

                motivo,

                comentario.getComentario()
        );
    }

    // =====================
    // LISTAR REPORTES
    // =====================

    @GetMapping
    public List<ReporteComentario>
    listar(){

        return service.listarTodos();
    }

    // =====================
    // MARCAR REVISADO
    // =====================

    @PutMapping("/revisar/{id}")
    public void revisar(

            @PathVariable String id
    ){

        service.revisar(id);
    }

    // =====================
    // ELIMINAR COMENTARIO
    // =====================

    @DeleteMapping("/comentario/{comentarioId}")
    public void eliminarComentario(

            @PathVariable String comentarioId
    ){

        comentarioRepo.deleteById(
                comentarioId
        );
    }
}