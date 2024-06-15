package com.alura.literatura.moduls;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

// Anotación para indicar que esta clase es una entidad JPA
@Entity
// Anotación para especificar el nombre de la tabla en la base de datos
@Table(name = "autores")
public class Autor
{
    // Anotación para indicar que este campo es la clave primaria
    @Id
    // Anotación para indicar que el valor de este campo será generado automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private int nacimiento;
    private int fallecimiento;
    // Anotación para indicar una relación uno a muchos con la entidad Libro
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros; // Lista de libros asociados a este autor

    // Constructor para inicializar un autor con nombre, nacimiento y fallecimiento
    public Autor(String nombre, Integer nacimiento, Integer fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento != null ? nacimiento : 0;
        this.fallecimiento = fallecimiento != null ? fallecimiento : 0;
    }

    public Autor() {
    }

    public String getNombre() {
        return nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setFallecimiento(int fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Método para generar una representación en cadena del autor y sus libros
    @Override
    public String toString() {
        return """
                \nAutor: %s
                Fecha nacimiento: %s
                Fecha Fallecimiento: %s
                Libros: %s
                """.formatted(nombre, nacimiento, fallecimiento, libros.stream().map(libro -> libro.getTitulo()).collect(Collectors.toList()));
    }
}
