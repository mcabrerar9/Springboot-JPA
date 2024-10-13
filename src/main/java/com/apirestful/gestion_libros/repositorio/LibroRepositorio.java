
package com.apirestful.gestion_libros.repositorio;

import com.apirestful.gestion_libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
}
