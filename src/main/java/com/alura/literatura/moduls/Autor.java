package com.alura.literatura.moduls;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private int nacimiento;
    private int fallecimiento;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros;

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
