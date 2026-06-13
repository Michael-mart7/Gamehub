package com.gamehub.repository;

import com.gamehub.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio MongoDB para la entidad Usuario.
 * MongoRepository provee CRUD básico automáticamente.
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    // Buscar usuario por correo (usado en login)
    Optional<Usuario> findByCorreo(String correo);

    // Verificar si un correo ya está registrado
    boolean existsByCorreo(String correo);

    // Listar usuarios por rol
    List<Usuario> findByRol(String rol);

    // Listar solo usuarios activos
    List<Usuario> findByActivoTrue();

    // Buscar usuarios por nombre (búsqueda parcial, ignora mayúsculas)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}