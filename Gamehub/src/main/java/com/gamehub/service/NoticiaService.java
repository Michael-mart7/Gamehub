package com.gamehub.service;

import com.gamehub.model.Noticia;
import com.gamehub.repository.NoticiaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaService {

    private final NoticiaRepository repo;

    public NoticiaService(
            NoticiaRepository repo
    ){
        this.repo = repo;
    }

    // =====================
    // CREAR
    // =====================

    public Noticia crear(

            String titulo,
            String contenido,
            String imagen,
            String tipo,
            String autor
    ){

        Noticia noticia =
                new Noticia();

        noticia.setTitulo(
                titulo
        );

        noticia.setContenido(
                contenido
        );

        noticia.setImagen(
                imagen
        );

        noticia.setTipo(
                tipo
        );

        noticia.setAutor(
                autor
        );

        return repo.save(
                noticia
        );
    }

    // =====================
    // LISTAR ACTIVAS
    // =====================

    public List<Noticia>
    activas(){

        return repo
                .findByActivaTrueOrderByFechaDesc();
    }

    // =====================
    // LISTAR TODAS
    // =====================

    public List<Noticia>
    listarTodas(){

        return repo
                .findAllByOrderByFechaDesc();
    }

    // =====================
    // ELIMINAR
    // =====================

    public void eliminar(
            String id
    ){

        repo.deleteById(id);
    }

    // =====================
    // DESACTIVAR
    // =====================

    public Noticia cambiarEstado(

            String id,

            boolean activa
    ){

        Noticia noticia =
                repo.findById(id)
                        .orElseThrow();

        noticia.setActiva(
                activa
        );

        return repo.save(
                noticia
        );
    }
    
    public Noticia actualizar(

            String id,

            String titulo,

            String contenido,

            String imagen,

            String tipo
    ){

        Noticia noticia =
                repo.findById(id)
                        .orElseThrow();

        noticia.setTitulo(
                titulo
        );

        noticia.setContenido(
                contenido
        );

        noticia.setImagen(
                imagen
        );

        noticia.setTipo(
                tipo
        );

        return repo.save(
                noticia
        );
    }
}