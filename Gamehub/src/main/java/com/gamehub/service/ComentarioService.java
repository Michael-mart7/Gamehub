package com.gamehub.service;

import com.gamehub.model.Comentario;
import com.gamehub.repository.ComentarioRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository repo;

    public ComentarioService(
            ComentarioRepository repo
    ){

        this.repo = repo;
    }

    // =====================
    // COMENTAR
    // =====================

    public Comentario comentar(

            String correoJugador,

            String juegoId,

            String texto
    ){

        Comentario comentario =
                new Comentario();

        comentario.setCorreoJugador(
                correoJugador
        );

        comentario.setJuegoId(
                juegoId
        );

        comentario.setComentario(
                texto
        );

        comentario.setFecha(
                LocalDateTime.now()
        );

        return repo.save(
                comentario
        );
    }

    // =====================
    // LISTAR
    // =====================

    public List<Comentario>
    listarComentarios(

            String juegoId
    ){

        return repo
                .findByJuegoIdOrderByFechaDesc(
                        juegoId
                );
    }
    
 // ELIMINAR COMENTARIO
 // =====================

 public void eliminarComentario(
         String comentarioId
 ){

     repo.deleteById(
             comentarioId
     );
 }
}