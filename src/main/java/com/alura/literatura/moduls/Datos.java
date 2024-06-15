package com.alura.literatura.moduls;

// Importaciones necesarias para las anotaciones de Jackson
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Anotación para ignorar propiedades desconocidas durante la deserialización JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        // Anotación para indicar que el campo 'resultados' debe mapearse desde la propiedad 'results' en el JSON
        @JsonAlias("results")List<DatosLibro> resultados)// Lista de objetos DatosLibro que contiene los resultados deserializados del JSON
{
}
