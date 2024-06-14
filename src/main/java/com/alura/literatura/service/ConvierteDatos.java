package com.alura.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvertirDatos{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertir(String json, Class<T> clase) throws JsonProcessingException {
        return mapper.readValue(json, clase);
    }
}
