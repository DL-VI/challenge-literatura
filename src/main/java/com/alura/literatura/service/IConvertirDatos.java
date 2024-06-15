package com.alura.literatura.service;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConvertirDatos {
    // Método genérico para convertir una cadena JSON a un objeto de la clase especificada
    <T>T convertir(String json, Class<T> clase) throws JsonProcessingException;
}
