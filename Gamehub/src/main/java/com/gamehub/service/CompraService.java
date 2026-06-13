package com.gamehub.service;

import com.gamehub.model.Compra;
import com.gamehub.model.Juego;
import com.gamehub.model.Usuario;

import com.gamehub.repository.JuegoRepository;
import com.gamehub.repository.CompraRepository;
import com.gamehub.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    private final CompraRepository repo;

    private final JuegoRepository juegoRepository;

    private final UsuarioRepository usuarioRepository;

    public CompraService(

            CompraRepository repo,

            JuegoRepository juegoRepository,

            UsuarioRepository usuarioRepository
    ){

        this.repo = repo;

        this.juegoRepository =
                juegoRepository;

        this.usuarioRepository =
                usuarioRepository;
    }

    // =====================
    // COMPRAR VIDEOJUEGO
    // =====================

    public Compra comprar(
            String correoJugador,
            String juegoId
    ){

        var existente =

                repo.findByCorreoJugadorAndJuegoId(
                        correoJugador,
                        juegoId
                );

        if(existente.isPresent()){

            throw new RuntimeException(
                    "Ya tienes este videojuego"
            );
        }

        Usuario usuario =

                usuarioRepository
                        .findByCorreo(correoJugador)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Usuario no encontrado"
                                )
                        );

        Juego juego =

                juegoRepository
                        .findById(juegoId)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Juego no encontrado"
                                )
                        );

        if(usuario.getSaldo() < juego.getPrecio()){

            throw new RuntimeException(
                    "Saldo insuficiente"
            );
        }

        // =====================
        // DESCONTAR SALDO
        // =====================

        usuario.setSaldo(

                usuario.getSaldo()
                        - juego.getPrecio()
        );

        usuarioRepository.save(
                usuario
        );

        // =====================
        // REGISTRAR COMPRA
        // =====================

        Compra compra =
                new Compra();

        compra.setCorreoJugador(
                correoJugador
        );

        compra.setJuegoId(
                juegoId
        );

        compra.setTituloJuego(
                juego.getTitulo()
        );

        compra.setPrecioPagado(
                juego.getPrecio()
        );

        return repo.save(
                compra
        );
    }

    // =====================
    // BIBLIOTECA DEL JUGADOR
    // =====================

    public List<Compra>
    obtenerBiblioteca(
            String correoJugador
    ){

        return repo.findByCorreoJugador(
                correoJugador
        );
    }

    // =====================
    // VALIDAR COMPRA
    // =====================

    public boolean yaComprado(
            String correoJugador,
            String juegoId
    ){

        return repo
                .findByCorreoJugadorAndJuegoId(
                        correoJugador,
                        juegoId
                )
                .isPresent();
    }

    // =====================
    // BIBLIOTECA COMPLETA
    // =====================

    public List<Juego>
    obtenerJuegosBiblioteca(
            String correoJugador
    ){

        List<Compra> compras =

                repo.findByCorreoJugador(
                        correoJugador
                );

        return compras.stream()

                .map(compra ->

                        juegoRepository
                                .findById(
                                        compra.getJuegoId()
                                )
                                .orElse(null)
                )

                .filter(juego -> juego != null)

                .toList();
    }
}