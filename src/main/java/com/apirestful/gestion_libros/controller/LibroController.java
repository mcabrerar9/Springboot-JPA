
package com.apirestful.gestion_libros.controller;

import com.apirestful.gestion_libros.model.Libro;
import com.apirestful.gestion_libros.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Gestión de libros en la biblioteca")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Operation(summary = "Obtener todos los libros")
    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroService.obtenerTodosLosLibros();
    }

    @Operation(summary = "Obtener un libro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        Optional<Libro> libro = libroService.obtenerLibroPorId(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo libro")
    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @Operation(summary = "Actualizar un libro")
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        Optional<Libro> libroExistente = libroService.obtenerLibroPorId(id);
        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            libro.setTitulo(libroDetalles.getTitulo());
            libro.setAutor(libroDetalles.getAutor());
            libro.setIsbn(libroDetalles.getIsbn());
            libro.setFechaPublicacion(libroDetalles.getFechaPublicacion());
            libroService.guardarLibro(libro);
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un libro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        Optional<Libro> libro = libroService.obtenerLibroPorId(id);
        if (libro.isPresent()) {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
