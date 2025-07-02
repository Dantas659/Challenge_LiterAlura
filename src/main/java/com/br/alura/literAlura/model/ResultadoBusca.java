package com.br.alura.literAlura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoBusca(
    @JsonAlias("results") List<DadosLivro> results
) {}


    

