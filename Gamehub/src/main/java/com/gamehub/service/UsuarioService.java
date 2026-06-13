package com.gamehub.service;

import com.gamehub.model.Usuario;
import com.gamehub.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para la entidad Usuario.
 * Aquí se cifran contraseñas y se validan reglas de negocio.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    // ── Crear usuario ────────────────────────────────────────────
    public Usuario crear(Usuario usuario) {
        if (repo.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }
        // Cifrar la contraseña antes de guardar
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        // Rol por defecto si no se especifica
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("JUGADOR");
        }
        return repo.save(usuario);
    }

    // ── Listar todos ─────────────────────────────────────────────
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    // ── Listar activos ───────────────────────────────────────────
    public List<Usuario> listarActivos() {
        return repo.findByActivoTrue();
    }

    // ── Buscar por ID ────────────────────────────────────────────
    public Optional<Usuario> buscarPorId(String id) {
        return repo.findById(id);
    }

    // ── Buscar por correo ─────────────────────────────────────────
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    // ── Actualizar usuario ────────────────────────────────────────
    public Usuario actualizar(String id, Usuario datos) {
        Usuario existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // Solo actualiza los campos que vienen rellenos
        if (datos.getNombre()    != null) existente.setNombre(datos.getNombre());
        if (datos.getRol()       != null) existente.setRol(datos.getRol());
        if (datos.getDescripcion()!= null) existente.setDescripcion(datos.getDescripcion());
        if (datos.getFotoPerfil()!= null) existente.setFotoPerfil(datos.getFotoPerfil());

        // Si mandan nueva contraseña, la ciframos
        if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
            existente.setPassword(encoder.encode(datos.getPassword()));
        }

        return repo.save(existente);
    }

    // ── Activar / Desactivar ──────────────────────────────────────
    public Usuario cambiarEstado(String id, boolean activo) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        usuario.setActivo(activo);
        return repo.save(usuario);
    }

    // ── Eliminar ──────────────────────────────────────────────────
    public void eliminar(String id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        repo.deleteById(id);
    }

    // ── Validar contraseña (para login) ──────────────────────────
    public boolean validarPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    // ── Listar por rol ────────────────────────────────────────────
    public List<Usuario> listarPorRol(String rol) {
        return repo.findByRol(rol);
    }

    // ── Buscar por nombre ─────────────────────────────────────────
    public List<Usuario> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
} 