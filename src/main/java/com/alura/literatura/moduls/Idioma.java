package com.alura.literatura.moduls;

public enum Idioma {
    // Definición de los valores de la enumeración con sus respectivas siglas
    ESPAÑOL("es"),
    INGLES("en"),
    OTRO("otro");

    private String sigla;

    Idioma(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    // Método estático para obtener un valor de la enumeración a partir de un texto
    public static Idioma getIdioma(String texto) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.sigla.equals(texto)) // Compara el texto proporcionado con la sigla de cada idioma
                return idioma;
        }
        return null;
    }
}
