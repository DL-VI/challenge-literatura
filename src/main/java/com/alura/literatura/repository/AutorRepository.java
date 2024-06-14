package com.alura.literatura.repository;

import com.alura.literatura.moduls.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento < :fecha AND a.fallecimiento > :fecha OR a.fallecimiento IS NULL")
    List<Autor> buscarAutorPorFecha(int fecha);

    Autor findByNombreIgnoreCaseContaining(String nombre);

    @Query(value = "SELECT MAX(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    int edadMayor();

    @Query(value = "SELECT nombre FROM autores WHERE (fallecimiento - nacimiento) = :edadMayor", nativeQuery = true)
    String nombreAutorMayor(int edadMayor);

    @Query(value = "SELECT MIN(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    int edadMenor();

    @Query(value = "SELECT nombre FROM autores WHERE (fallecimiento - nacimiento) = :edadMenor", nativeQuery = true)
    String nombreAutorMenor(int edadMenor);

    @Query(value = "SELECT AVG(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    double mediaEdad();
}
