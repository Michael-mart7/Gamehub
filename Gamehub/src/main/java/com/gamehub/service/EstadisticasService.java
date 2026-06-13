package com.gamehub.service;

import com.gamehub.model.EstadisticasDTO;
import com.gamehub.model.Compra;
import com.gamehub.model.Juego;
import com.gamehub.repository.*;
import com.gamehub.model.Usuario;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {

    private final UsuarioRepository usuarioRepo;

    private final JuegoRepository juegoRepo;

    private final CompraRepository compraRepo;

    private final ComentarioRepository comentarioRepo;

    private final ReporteComentarioRepository reporteRepo;

    private final TicketSoporteRepository ticketRepo;

    private final NoticiaRepository noticiaRepo;

    public EstadisticasService(

            UsuarioRepository usuarioRepo,

            JuegoRepository juegoRepo,

            CompraRepository compraRepo,

            ComentarioRepository comentarioRepo,

            ReporteComentarioRepository reporteRepo,

            TicketSoporteRepository ticketRepo,

            NoticiaRepository noticiaRepo
    ){

        this.usuarioRepo = usuarioRepo;

        this.juegoRepo = juegoRepo;

        this.compraRepo = compraRepo;

        this.comentarioRepo = comentarioRepo;

        this.reporteRepo = reporteRepo;

        this.ticketRepo = ticketRepo;

        this.noticiaRepo = noticiaRepo;
    }

    public EstadisticasDTO obtener(
            String correo
    ){

        EstadisticasDTO dto =
                new EstadisticasDTO();

        Usuario usuario =

                usuarioRepo
                        .findByCorreo(correo)
                        .orElseThrow();

        dto.setRol(
                usuario.getRol()
        );
     // =====================
     // JUEGO MÁS COMPRADO GLOBAL
     // =====================

     Map<String, Long> conteoGlobal =

             compraRepo.findAll()

                     .stream()

                     .collect(

                             Collectors.groupingBy(

                                     Compra::getJuegoId,

                                     Collectors.counting()
                             )
                     );

     Optional<String> juegoTopGlobal =

             conteoGlobal.entrySet()

                     .stream()

                     .max(
                             Map.Entry.comparingByValue()
                     )

                     .map(
                             Map.Entry::getKey
                     );

     if(juegoTopGlobal.isPresent()){

         Optional<Juego> juego =

                 juegoRepo.findById(
                         juegoTopGlobal.get()
                 );

         dto.setJuegoMasComprado(

                 juego
                         .map(Juego::getTitulo)
                         .orElse("Sin datos")
         );

     }else{

         dto.setJuegoMasComprado(
                 "Sin compras"
         );
     }
        // =====================
        // ADMIN
        // =====================

        if("ADMIN".equals(usuario.getRol())){

            dto.setUsuarios(
                    usuarioRepo.count()
            );

            dto.setVideojuegos(
                    juegoRepo.count()
            );

            dto.setCompras(
                    compraRepo.count()
            );
            double ingresos =

                    compraRepo.findAll()

                            .stream()

                            .mapToDouble(compra ->

                                    compra.getPrecioPagado() == null
                                    ? 0
                                    : compra.getPrecioPagado()
                            )

                            .sum();

            dto.setIngresos(
                    ingresos
            );

            dto.setComentarios(
                    comentarioRepo.count()
            );

            dto.setReportes(
                    reporteRepo.count()
            );

            dto.setTickets(
                    ticketRepo.count()
            );

            dto.setNoticias(
                    noticiaRepo.count()
            );

            LocalDate hoy =
                    LocalDate.now();

            long comprasHoy =

                    compraRepo.findAll()

                            .stream()

                            .filter(c ->

                                    c.getFechaCompra()
                                            .toLocalDate()
                                            .equals(hoy)
                            )

                            .count();

            dto.setComprasHoy(
                    comprasHoy
            );

            long comprasMes =

                    compraRepo.findAll()

                            .stream()

                            .filter(c ->

                                    c.getFechaCompra()
                                            .getMonthValue()

                                            ==

                                            hoy.getMonthValue()

                                            &&

                                            c.getFechaCompra()
                                                    .getYear()

                                                    ==

                                                    hoy.getYear()
                            )

                            .count();

            dto.setComprasMes(
                    comprasMes
            );

            Map<String, Long> conteo =

                    compraRepo.findAll()

                            .stream()

                            .collect(

                                    Collectors.groupingBy(

                                            Compra::getJuegoId,

                                            Collectors.counting()
                                    )
                            );

            Optional<String> juegoTop =

                    conteo.entrySet()

                            .stream()

                            .max(
                                    Map.Entry.comparingByValue()
                            )

                            .map(
                                    Map.Entry::getKey
                            );

            if(juegoTop.isPresent()){

                Optional<Juego> juego =

                        juegoRepo.findById(
                                juegoTop.get()
                        );

                dto.setJuegoMasComprado(

                        juego
                                .map(Juego::getTitulo)
                                .orElse("Sin datos")
                );

            }else{

                dto.setJuegoMasComprado(
                        "Sin compras"
                );
            }

            return dto;
        }

        // =====================
        // DESARROLLADOR
        // =====================

        if("DESARROLLADOR".equals(usuario.getRol())){

            List<Juego> misJuegos =

                    juegoRepo.findByDesarrollador(
                            correo
                    );

            dto.setVideojuegos(
                    misJuegos.size()
            );

            Set<String> idsJuegos =

                    misJuegos.stream()

                            .map(Juego::getId)

                            .collect(
                                    Collectors.toSet()
                            );

            List<Compra> comprasMisJuegos =

                    compraRepo.findAll()

                            .stream()

                            .filter(compra ->

                                    idsJuegos.contains(
                                            compra.getJuegoId()
                                    )
                            )

                            .toList();

            dto.setCompras(
                    comprasMisJuegos.size()
            );
            double ingresos =

                    comprasMisJuegos.stream()

                            .mapToDouble(compra ->

                                    compra.getPrecioPagado() == null
                                    ? 0
                                    : compra.getPrecioPagado()
                            )

                            .sum();

            dto.setIngresos(
                    ingresos
            );

            long comentarios =

                    idsJuegos.stream()

                            .mapToLong(idJuego ->

                                    comentarioRepo
                                            .findByJuegoIdOrderByFechaDesc(
                                                    idJuego
                                            )
                                            .size()
                            )

                            .sum();

            dto.setComentarios(
                    comentarios
            );

            LocalDate hoy =
                    LocalDate.now();

            long comprasHoy =

                    comprasMisJuegos.stream()

                            .filter(c ->

                                    c.getFechaCompra()
                                            .toLocalDate()
                                            .equals(hoy)
                            )

                            .count();

            dto.setComprasHoy(
                    comprasHoy
            );

            long comprasMes =

                    comprasMisJuegos.stream()

                            .filter(c ->

                                    c.getFechaCompra()
                                            .getMonthValue()

                                            ==

                                            hoy.getMonthValue()

                                            &&

                                            c.getFechaCompra()
                                                    .getYear()

                                                    ==

                                                    hoy.getYear()
                            )

                            .count();

            dto.setComprasMes(
                    comprasMes
            );

            Map<String, Long> conteo =

                    comprasMisJuegos.stream()

                            .collect(

                                    Collectors.groupingBy(

                                            Compra::getJuegoId,

                                            Collectors.counting()
                                    )
                            );

            Optional<String> juegoTop =

                    conteo.entrySet()

                            .stream()

                            .max(
                                    Map.Entry.comparingByValue()
                            )

                            .map(
                                    Map.Entry::getKey
                            );

            if(juegoTop.isPresent()){

                Optional<Juego> juego =

                        juegoRepo.findById(
                                juegoTop.get()
                        );

                dto.setJuegoMasComprado(

                        juego
                                .map(Juego::getTitulo)
                                .orElse("Sin datos")
                );

            }else{

                dto.setJuegoMasComprado(
                        "Sin compras"
                );
            }

            return dto;
        }

        // =====================
        // MODERADOR
        // =====================

        if("MODERADOR".equals(usuario.getRol())){

            dto.setComentarios(
                    comentarioRepo.count()
            );

            dto.setReportes(
                    reporteRepo.count()
            );

            return dto;
        }

        // =====================
        // SOPORTE
        // =====================

        if("SOPORTE".equals(usuario.getRol())){

            dto.setTickets(
                    ticketRepo.count()
            );

            return dto;
        }

        // =====================
        // EDITOR
        // =====================

        if("EDITOR".equals(usuario.getRol())){

            dto.setNoticias(
                    noticiaRepo.count()
            );

            return dto;
        }

        return dto;
    }
    
    
}