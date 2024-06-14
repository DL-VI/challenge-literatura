package com.alura.literatura.repository;

import com.alura.literatura.moduls.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long>
{
    List<Libro> findByIdioma(String idioma);

    @Query(value = "SELECT titulo FROM libros WHERE descargas = (SELECT MAX(descargas) FROM libros)", nativeQuery = true)
    String tituloDellibroConMayorDescarga();

    @Query(value = "SELECT titulo FROM libros WHERE descargas = (SELECT MIN(descargas) FROM libros)", nativeQuery = true)
    String tituloDellibroConMenorDescarga();
}
