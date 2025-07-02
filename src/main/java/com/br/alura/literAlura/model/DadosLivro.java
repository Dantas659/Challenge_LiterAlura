package com.br.alura.literAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosLivro(
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DadosAutor> authors,
    @JsonAlias("languages") List<String> idioma,
    @JsonAlias("download_count") Double numeroDownloads
) {}

