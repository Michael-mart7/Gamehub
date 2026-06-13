package com.gamehub.controller;

import com.gamehub.model.Usuario;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.gamehub.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(
	    name = "Usuarios API",
	    description = "Operaciones para gestión de usuarios"
	)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    

    // LISTAR TODOS
    @GetMapping
    @Operation(
    	    summary = "Listar usuarios",
    	    description = "Obtiene todos los usuarios registrados"
    	)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }
    
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> cambiarEstado(
            @PathVariable String id,
            @RequestParam boolean activo){

        return ResponseEntity.ok(
                usuarioService.cambiarEstado(id, activo)
        );
    }
    

    // BUSCAR POR ID
    @GetMapping("/buscar")
    @Operation(
    	    summary = "Buscar usuario",
    	    description = "Obtiene un usuario mediante su ID"
    	)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> buscar(
            @RequestParam String nombre){

        return usuarioService.buscarPorNombre(nombre);
    }

    // CREAR
    @PostMapping
    @Operation(
    	    summary = "Crear usuario",
    	    description = "Registra un nuevo usuario"
    	)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {

        return ResponseEntity.ok(usuarioService.crear(usuario));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    @Operation(
    	    summary = "Actualizar usuario",
    	    description = "Actualiza la información de un usuario"
    	)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable String id,
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, usuario)
        );
    }
    

    // ELIMINAR
    @DeleteMapping("/{id}")
    @Operation(
    	    summary = "Eliminar usuario",
    	    description = "Elimina un usuario del sistema"
    	)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/saldo")
    public Double obtenerSaldo(
            Authentication auth
    ){

        Usuario usuario =

                usuarioService
                        .buscarPorCorreo(
                                auth.getName()
                        )
                        .orElseThrow();

        return usuario.getSaldo();
    }
 
}