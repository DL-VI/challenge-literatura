package com.alura.literatura.repository;

import com.alura.literatura.moduls.Idioma;
import com.alura.literatura.moduls.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
// Definición de la interfaz LibroRepository que extiende JpaRepository para manejar operaciones CRUD

public interface LibroRepository extends JpaRepository<Libro, Long>
{
    // Consulta para encontrar libros por idioma
    List<Libro> findByIdioma(Idioma idioma);

    // Consulta nativa para encontrar el título del libro con el mayor número de descargas
    @Query(value = "SELECT titulo FROM libros WHERE descargas = (SELECT MAX(descargas) FROM libros)", nativeQuery = true)
    String tituloDellibroConMayorDescarga();

    // Consulta nativa para encontrar el título del libro con el menor número de descargas
    @Query(value = "SELECT titulo FROM libros WHERE descargas = (SELECT MIN(descargas) FROM libros)", nativeQuery = true)
    String tituloDellibroConMenorDescarga();
}
