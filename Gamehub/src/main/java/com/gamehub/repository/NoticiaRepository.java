package com.gamehub.repository;

import com.gamehub.model.Noticia;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository
        extends MongoRepository<Noticia, String> {

    List<Noticia>
    findByActivaTrueOrderByFechaDesc();

    List<Noticia>
    findAllByOrderByFechaDesc();
}