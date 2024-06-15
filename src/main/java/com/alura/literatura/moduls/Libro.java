package com.alura.literatura.moduls;


import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // Anotación para especificar que este campo debe ser único y no nulo
    @Column(unique = true, nullable = false)
    private String titulo;
    // Anotación para indicar una relación muchos a uno con la entidad Autor
    @ManyToOne
    @JoinColumn(name = "autor_id") // Anotación para especificar la columna de unión en la tabla libros
    private Autor autor;
    // Anotación para especificar que este campo es un enumerado y que debe ser almacenado como una cadena
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private int descargas;

     public Libro(DatosLibro dtsLibro)
    {
        this.titulo = dtsLibro.titulo();
        // Asigna el idioma del libro desde DatosLibro, si no es nulo
        this.idioma = Idioma.getIdioma(dtsLibro.idioma().get(0)) != null ? Idioma.getIdioma(dtsLibro.idioma().get(0)) : Idioma.OTRO;
        this.descargas = dtsLibro.descargas();
    }

    public Libro() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString()
    {
        return """
                \nTitulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                """.formatted(titulo, autor.getNombre(), idioma.getSigla(), descargas);
    }
}
