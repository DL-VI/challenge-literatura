package com.alura.literatura.service;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConvertirDatos {
    <T>T convertir(String json, Class<T> clase) throws JsonProcessingException;
}
