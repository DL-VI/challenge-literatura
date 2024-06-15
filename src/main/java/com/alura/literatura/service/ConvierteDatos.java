package com.alura.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvertirDatos{
    // Instancia de ObjectMapper para la conversión de JSON
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertir(String json, Class<T> clase) throws JsonProcessingException {
        // Utiliza ObjectMapper para convertir el JSON a un objeto de la clase especificada
        return mapper.readValue(json, clase);
    }
}
