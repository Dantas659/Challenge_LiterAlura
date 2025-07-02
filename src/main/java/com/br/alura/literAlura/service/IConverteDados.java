package com.br.alura.literAlura.service;

public interface IConverteDados {
    <T> T obterDadosJson(String json, Class<T> classe);
}
