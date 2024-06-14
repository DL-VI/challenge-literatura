package com.alura.literatura.moduls;


import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private int descargas;

     public Libro(DatosLibro dtsLibro)
    {
        this.titulo = dtsLibro.titulo();
        this.idioma = dtsLibro.idioma().get(0);
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
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
                """.formatted(titulo, autor.getNombre(), idioma, descargas);
    }
}
