package com.gamehub.service;

import com.gamehub.model.ReporteComentario;
import com.gamehub.repository.ReporteComentarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteComentarioService {

    private final ReporteComentarioRepository repo;

    private final ComentarioService
            comentarioService;

    public ReporteComentarioService(

            ReporteComentarioRepository repo,

            ComentarioService comentarioService
    ){

        this.repo = repo;

        this.comentarioService =
                comentarioService;
    }

    // =====================
    // REPORTAR
    // =====================

    public ReporteComentario reportar(

            String comentarioId,
            String correo,
            String motivo,
            String comentarioTexto
    ){

        ReporteComentario reporte =
                new ReporteComentario();

        reporte.setComentarioId(
                comentarioId
        );

        reporte.setComentarioTexto(
                comentarioTexto
        );

        reporte.setCorreoReportante(
                correo
        );

        reporte.setMotivo(
                motivo
        );

        return repo.save(
                reporte
        );
    }

    // =====================
    // PENDIENTES
    // =====================

    public List<ReporteComentario>
    pendientes(){

        return repo.findByRevisadoFalse();
    }

    // =====================
    // LISTAR TODOS
    // =====================

    public List<ReporteComentario>
    listarTodos(){

        return repo.findAll();
    }

    // =====================
    // MARCAR REVISADO
    // =====================

    public void revisar(String id){

        ReporteComentario reporte =

                repo.findById(id)
                .orElseThrow();

        reporte.setRevisado(
                true
        );

        repo.save(reporte);
    }

    // =====================
    // ELIMINAR COMENTARIO
    // =====================

    public void eliminarComentario(
            String reporteId
    ){

        ReporteComentario reporte =

                repo.findById(
                        reporteId
                ).orElseThrow();

        comentarioService
                .eliminarComentario(

                        reporte.getComentarioId()
                );

        reporte.setRevisado(
                true
        );

        repo.save(reporte);
    }
}