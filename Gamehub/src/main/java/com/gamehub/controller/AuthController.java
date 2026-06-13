package com.gamehub.controller;

import com.gamehub.dto.AuthResponse;
import com.gamehub.dto.LoginRequest;
import com.gamehub.model.Usuario;
import com.gamehub.security.JwtUtil;
import com.gamehub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints públicos: registro y login.
 * No requieren token JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil        jwtUtil; 

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil        = jwtUtil;
    }

    // ── POST /api/auth/registro ───────────────────────
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.crear(usuario);
            String token  = jwtUtil.generarToken(nuevo.getCorreo(), nuevo.getRol());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(token, nuevo.getCorreo(),
                                           nuevo.getRol(), nuevo.getNombre()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── POST /api/auth/login ──────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return usuarioService.buscarPorCorreo(request.getCorreo())
                .filter(u -> usuarioService.validarPassword(request.getPassword(),
                                                             u.getPassword()))
                .filter(Usuario::isActivo)
                .map(u -> {
                    String token = jwtUtil.generarToken(u.getCorreo(), u.getRol());
                    return ResponseEntity.ok(
                            new AuthResponse(token, u.getCorreo(),
                                             u.getRol(), u.getNombre()));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build());
    }
}