package com.gamehub.repository;

import com.gamehub.model.Comentario;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository
        extends MongoRepository<Comentario, String> {

    List<Comentario>
    findByJuegoIdOrderByFechaDesc(
            String juegoId
    );
    void deleteById(String id);
}