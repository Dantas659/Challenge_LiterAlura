package com.br.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record DadosAutor(
    @JsonAlias("name") String nome,
    @JsonAlias("birth_year") Integer anoNascimento,
    @JsonAlias("death_year") Integer anoFalecimento
) {
   @Override
public String toString() {
    return String.format("""
            ----------------------------
            Autor: %s
            Ano de Nascimento: %d
            Ano de Falecimento: %d
            ----------------------------
            """, nome, anoNascimento, anoFalecimento);
}

}


