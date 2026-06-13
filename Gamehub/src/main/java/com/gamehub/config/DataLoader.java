package com.gamehub.config;

import com.gamehub.model.Usuario;
import com.gamehub.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Se ejecuta automáticamente al arrancar la aplicación.
 * Inserta el usuario administrador por defecto si no existe.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository repo;
    private final PasswordEncoder   encoder;

    public DataLoader(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        // ── Administrador por defecto ─────────────────────
        if (!repo.existsByCorreo("admin@gamehub.com")) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@gamehub.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRol("ADMIN");
            admin.setActivo(true);
            repo.save(admin);
            System.out.println("✅ Usuario admin creado: admin@gamehub.com / admin123");
        }

        // ── Jugador de prueba ─────────────────────────────
        if (!repo.existsByCorreo("jugador@gamehub.com")) {
            Usuario jugador = new Usuario();
            jugador.setNombre("Jugador Demo");
            jugador.setCorreo("jugador@gamehub.com");
            jugador.setPassword(encoder.encode("jugador123"));
            jugador.setRol("JUGADOR");
            jugador.setActivo(true);
            repo.save(jugador);
            System.out.println("✅ Usuario jugador creado: jugador@gamehub.com / jugador123");
        }
    }
}