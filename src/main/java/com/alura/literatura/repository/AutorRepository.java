package com.alura.literatura.repository;

import com.alura.literatura.moduls.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Definición de la interfaz AutorRepository que extiende JpaRepository para manejar operaciones CRUD
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Consulta para encontrar un autor por nombre ignorando mayúsculas y minúsculas
    Autor findByNombreIgnoreCase(String nombre);

    // Consulta personalizada para encontrar autores nacidos antes de una fecha dada y fallecidos después de esa fecha o que aún vivan
    @Query("SELECT a FROM Autor a WHERE a.nacimiento < :fecha AND a.fallecimiento > :fecha OR a.fallecimiento IS NULL")
    List<Autor> buscarAutorPorFecha(int fecha);

    // Consulta para encontrar un autor cuyo nombre contenga una cadena específica, ignorando mayúsculas y minúsculas
    List<Autor> findByNombreIgnoreCaseContaining(String nombre);

    // Consulta nativa para encontrar la mayor edad al fallecer entre los autores fallecidos
    @Query(value = "SELECT MAX(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    int edadMayor();

    // Consulta nativa para encontrar el nombre del autor con la mayor edad al fallecer
    @Query(value = "SELECT nombre FROM autores WHERE (fallecimiento - nacimiento) = :edadMayor", nativeQuery = true)
    String nombreAutorMayor(int edadMayor);

    // Consulta nativa para encontrar la menor edad al fallecer entre los autores fallecidos
    @Query(value = "SELECT MIN(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    int edadMenor();

    // Consulta nativa para encontrar el nombre del autor con la menor edad al fallecer
    @Query(value = "SELECT nombre FROM autores WHERE (fallecimiento - nacimiento) = :edadMenor", nativeQuery = true)
    String nombreAutorMenor(int edadMenor);

    // Consulta nativa para encontrar la edad promedio al fallecer entre los autores fallecidos
    @Query(value = "SELECT AVG(fallecimiento - nacimiento) FROM autores WHERE fallecimiento IS NOT NULL", nativeQuery = true)
    double mediaEdad();
}
