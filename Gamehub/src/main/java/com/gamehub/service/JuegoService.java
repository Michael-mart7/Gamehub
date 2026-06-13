package com.gamehub.service;

import com.gamehub.model.Juego;
import com.gamehub.repository.JuegoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    private final JuegoRepository repo;

    public JuegoService(JuegoRepository repo) {
        this.repo = repo;
    }

    // ─────────────────────────────────
    // CREAR JUEGO
    // ─────────────────────────────────
    public Juego crear(Juego juego) {

        juego.setPrecioOriginal(
                juego.getPrecio()
        );

        return repo.save(juego);
    }
    
    public void restaurarPrecio(
            String id
    ){

        Juego juego = repo.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Juego no encontrado"
                        ));

        // juegos antiguos sin precioOriginal
        if(juego.getPrecioOriginal() == null){

            juego.setPrecioOriginal(
                    juego.getPrecio()
            );
        }

        juego.setPrecio(
                juego.getPrecioOriginal()
        );

        juego.setDescuento(0.0);

        repo.save(juego);
    }
    
 // ─────────────────────────────────
 // APLICAR DESCUENTO
 // ─────────────────────────────────
 public Juego aplicarDescuento(

         String id,

         Double precio,

         Double descuento
 ){

     Juego juego = repo.findById(id)

             .orElseThrow(() ->
                     new RuntimeException(
                             "Juego no encontrado"
                     ));

     juego.setPrecio(precio);

     juego.setDescuento(descuento);

     return repo.save(juego);
 }

    // ─────────────────────────────────
    // LISTAR TODOS
    // ─────────────────────────────────
    public List<Juego> listarTodos() {

        return repo.findAll();
    }
    
  //Listar juego del propio desarrollador
    public List<Juego>
    listarPorDesarrollador(
            String desarrollador
    ){

        return repo.findByDesarrollador(
                desarrollador
        );
    }

    // ─────────────────────────────────
    // LISTAR ACTIVOS (CATÁLOGO)
    // ─────────────────────────────────
    public List<Juego> listarActivos(){

        return repo.findByActivoTrue();
    }

    // ─────────────────────────────────
    // BUSCAR POR ID
    // ─────────────────────────────────
    public Optional<Juego> buscarPorId(String id) {

        return repo.findById(id);
    }

    // ─────────────────────────────────
    // BUSCAR POR TÍTULO
    // ─────────────────────────────────
    public List<Juego> buscarPorTitulo(String titulo) {

        return repo.findByTituloContainingIgnoreCase(
                titulo
        );
    }
    
    

    // ─────────────────────────────────
    // FILTRAR POR GÉNERO
    // ─────────────────────────────────
    public List<Juego> listarPorGenero(
            String genero
    ) {

        return repo.findByGeneroIgnoreCase(
                genero
        );
    }

    // ─────────────────────────────────
    // FILTRAR POR PRECIO
    // ─────────────────────────────────
    public List<Juego> filtrarPorPrecio(
            Double min,
            Double max
    ) {

        return repo.findByPrecioBetween(
                min,
                max
        );
    }

    // ─────────────────────────────────
    // JUEGOS DESTACADOS
    // ─────────────────────────────────
    public List<Juego> destacados() {

        return repo.findByDestacadoTrue();
    }

    // ─────────────────────────────────
    // ACTUALIZAR
    // ─────────────────────────────────
    public Juego actualizar(
            String id,
            Juego datos
    ) {

        Juego juego = repo.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Juego no encontrado"
                        ));

        if(datos.getTitulo() != null)
            juego.setTitulo(datos.getTitulo());

        if(datos.getDescripcion() != null)
            juego.setDescripcion(
                    datos.getDescripcion()
            );

        if(datos.getGenero() != null)
            juego.setGenero(
                    datos.getGenero()
            );

        if(datos.getEtiquetas() != null)
            juego.setEtiquetas(
                    datos.getEtiquetas()
            );

        if(datos.getPrecio() != null){

            juego.setPrecio(
                    datos.getPrecio()
            );

            // nuevo precio base
            juego.setPrecioOriginal(
                    datos.getPrecio()
            );

            // quitar descuento anterior
            juego.setDescuento(0.0);
        }

        if(datos.getPortada() != null)
            juego.setPortada(
                    datos.getPortada()
            );

        if(datos.getDesarrollador() != null)
            juego.setDesarrollador(
                    datos.getDesarrollador()
            );

        if(datos.getStock() != null)
            juego.setStock(
                    datos.getStock()
            );

        juego.setActivo(
                datos.isActivo()
        );

        juego.setDestacado(
                datos.isDestacado()
        );

        if(datos.getDescuento() != null)
            juego.setDescuento(
                    datos.getDescuento()
            );

        return repo.save(juego);
    }

    // ─────────────────────────────────
    // ACTIVAR / SUSPENDER
    // ─────────────────────────────────
    public Juego cambiarEstado(
            String id,
            boolean activo
    ) {

        Juego juego = repo.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Juego no encontrado"
                        ));

        juego.setActivo(activo);

        return repo.save(juego);
    }

    // ─────────────────────────────────
    // ELIMINAR
    // ─────────────────────────────────
    public void eliminar(String id) {

        if(!repo.existsById(id)) {

            throw new RuntimeException(
                    "Juego no encontrado"
            );
        }

        repo.deleteById(id);
    }
}