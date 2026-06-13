package com.gamehub.repository;

import com.gamehub.model.ReporteComentario;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReporteComentarioRepository
        extends MongoRepository<ReporteComentario,String> {

    List<ReporteComentario>
    findByRevisadoFalse();
}