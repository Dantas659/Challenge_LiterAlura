package com.br.alura.literAlura.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Livro> livrosAutor = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public Integer getAnoNascimento() {
        return anoNascimento;
    }
    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }
    public List<Livro> getLivrosAutor() {
        return livrosAutor;
    }

    
    public void setId(Long id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }
    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }
    public void setLivrosAutor(List<Livro> livrosAutor) {
        livrosAutor.forEach(l -> l.setAutor(this));
        this.livrosAutor = livrosAutor;
    }

   @Override
public String toString() {
    return """
            ----------------------------
            Autor: """ + nome + """

            Ano de Nascimento: """ + anoNascimento + """

            Ano de Falecimento: """ + anoFalecimento + """

            Livro(s) """ + livrosAutor + """

            ----------------------------
            """;
}



}
