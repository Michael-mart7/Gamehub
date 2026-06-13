package com.gamehub.controller;

import com.gamehub.model.Juego;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import com.gamehub.service.JuegoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
	    name = "Juegos API",
	    description = "Operaciones para gestión de videojuegos"
	)
@RequestMapping("/api/juegos")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(
            JuegoService juegoService
    ) {
        this.juegoService = juegoService;
    }

    // ─────────────────────────────
    // LISTAR TODOS
    // ─────────────────────────────
    @GetMapping
    @Operation(
    	    summary = "Listar videojuegos",
    	    description = "Obtiene videojuegos según el rol del usuario"
    	)
    public List<Juego> listarTodos(
            Authentication auth
    ) {

        boolean admin = auth
                .getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority()
                                .equals("ROLE_ADMIN")
                );

        if(admin){

            return juegoService
                    .listarTodos();
        }

        return juegoService
                .listarPorDesarrollador(
                        auth.getName()
                );
    }
    
    @PatchMapping("/{id}/restaurar-precio")
    public ResponseEntity<?> restaurarPrecio(

            @PathVariable String id
    ){

        juegoService.restaurarPrecio(id);

        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}/descuento")
    public ResponseEntity<Juego>
    aplicarDescuento(

            @PathVariable String id,

            @RequestBody Juego datos
    ){

        return ResponseEntity.ok(

                juegoService.aplicarDescuento(

                        id,

                        datos.getPrecio(),

                        datos.getDescuento()

                )
        );
    }

    // ─────────────────────────────
    // LISTAR SOLO ACTIVOS
    // ─────────────────────────────
    @GetMapping("/activos")
    @Operation(
    	    summary = "Catálogo público",
    	    description = "Obtiene videojuegos activos del catálogo"
    	)
    public List<Juego> listarActivos() {

        return juegoService.listarActivos();
    }

    // ─────────────────────────────
    // BUSCAR POR ID
    // ─────────────────────────────
    @GetMapping("/{id}")
    @Operation(
    	    summary = "Detalle videojuego",
    	    description = "Obtiene información detallada de un videojuego"
    	)
    public ResponseEntity<Juego> buscarPorId(
            @PathVariable String id
    ) {

        return juegoService.buscarPorId(id)

                .map(ResponseEntity::ok)

                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // ─────────────────────────────
    // CREAR JUEGO
    // ADMIN / DESARROLLADOR
    // ─────────────────────────────
    @PostMapping
    @Operation(
    	    summary = "Crear videojuego",
    	    description = "Registra un videojuego nuevo"
    	)

    @PreAuthorize(
            "hasRole('ADMIN') or hasRole('DESARROLLADOR')"
    )
    public ResponseEntity<Juego> crear(

            @RequestBody Juego juego,

            Authentication auth
    ) {

        juego.setDesarrollador(
                auth.getName()
        );

        return ResponseEntity.ok(
                juegoService.crear(juego)
        );
    }
    // ─────────────────────────────
    // EDITAR
    // ─────────────────────────────
    @PutMapping("/{id}")
    @Operation(
    	    summary = "Actualizar videojuego",
    	    description = "Actualiza un videojuego existente"
    	)
    @PreAuthorize(
            "hasRole('ADMIN') or hasRole('DESARROLLADOR')"
    )
    public ResponseEntity<Juego> actualizar(

            @PathVariable String id,

            @RequestBody Juego juego
    ) {

        return ResponseEntity.ok(

                juegoService.actualizar(
                        id,
                        juego
                )
        );
    }

    // ─────────────────────────────
    // ELIMINAR
    // ─────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(
    	    summary = "Eliminar videojuego",
    	    description = "Elimina un videojuego"
    	)
    @PreAuthorize(
            "hasRole('ADMIN') or hasRole('DESARROLLADOR')"
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable String id
    ) {

        juegoService.eliminar(id);

        return ResponseEntity.noContent()
                .build();
    }

    // ─────────────────────────────
    // CAMBIAR ESTADO
    // suspendido / activo
    // ─────────────────────────────
    @PatchMapping("/{id}/estado")
    @Operation(
    	    summary = "Cambiar estado",
    	    description = "Activa o suspende un videojuego"
    	)
    @PreAuthorize(
        "hasRole('ADMIN') or hasRole('DESARROLLADOR')"
    )
    public ResponseEntity<Juego> cambiarEstado(

            @PathVariable String id,

            @RequestParam boolean activo
    ) {

        return ResponseEntity.ok(

                juegoService.cambiarEstado(
                        id,
                        activo
                )
        );
    }

    // ─────────────────────────────
    // BUSCAR POR TITULO
    // ─────────────────────────────
    @GetMapping("/buscar")
    public List<Juego> buscar(

            @RequestParam String titulo
    ) {

        return juegoService
                .buscarPorTitulo(titulo);
    }

    // ─────────────────────────────
    // FILTRAR GENERO
    // ─────────────────────────────
    @GetMapping("/genero/{genero}")
    public List<Juego> porGenero(

            @PathVariable String genero
    ) {

        return juegoService
                .listarPorGenero(genero);
    }

    // ─────────────────────────────
    // FILTRAR PRECIO
    // ─────────────────────────────
    @GetMapping("/precio")
    public List<Juego> precio(

            @RequestParam Double min,

            @RequestParam Double max
    ) {

        return juegoService
                .filtrarPorPrecio(min, max);
    }

    // ─────────────────────────────
    // DESTACADOS
    // ─────────────────────────────
    @GetMapping("/destacados")
    public List<Juego> destacados() {

        return juegoService.destacados();
    }
    
    
}