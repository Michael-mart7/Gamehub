package com.gamehub.controller;

import com.gamehub.model.Noticia;
import com.gamehub.service.NoticiaService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    private final NoticiaService service;

    public NoticiaController(
            NoticiaService service
    ){
        this.service = service;
    }

    // =====================
    // CREAR
    // =====================

    @PostMapping
    public Noticia crear(

            @RequestParam String titulo,

            @RequestParam String contenido,

            @RequestParam(required = false)
            String imagen,

            @RequestParam String tipo,

            Authentication auth
    ){

        return service.crear(

                titulo,

                contenido,

                imagen,

                tipo,

                auth.getName()
        );
    }

    // =====================
    // ACTIVAS (PUBLICAS)
    // =====================

    @GetMapping("/activas")
    public List<Noticia>
    activas(){

        return service.activas();
    }

    // =====================
    // TODAS (EDITOR)
    // =====================

    @GetMapping
    public List<Noticia>
    listarTodas(){

        return service.listarTodas();
    }

    // =====================
    // ELIMINAR
    // =====================

    @DeleteMapping("/{id}")
    public void eliminar(

            @PathVariable
            String id
    ){

        service.eliminar(id);
    }

    // =====================
    // ACTIVAR / DESACTIVAR
    // =====================

    @PatchMapping("/{id}")
    public Noticia cambiarEstado(

            @PathVariable
            String id,

            @RequestParam
            boolean activa
    ){

        return service
                .cambiarEstado(
                        id,
                        activa
                );
    }
    
    @PutMapping("/{id}")
    public Noticia actualizar(

            @PathVariable
            String id,

            @RequestParam String titulo,

            @RequestParam String contenido,

            @RequestParam(required = false)
            String imagen,

            @RequestParam String tipo
    ){

        return service.actualizar(

                id,

                titulo,

                contenido,

                imagen,

                tipo
        );
    }
}