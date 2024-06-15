package com.alura.literatura.moduls;

public enum Idioma {
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

    public static Idioma getIdioma(String texto) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.sigla.equals(texto))
                return idioma;
        }
        return null;
    }
}
