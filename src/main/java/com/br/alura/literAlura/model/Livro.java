package com.br.alura.literAlura.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double numeroDownloads;

    public Livro() {}

    public Livro(DadosLivro dados, Autor autorExistente) {
        this.titulo = dados.titulo();
        this.autor = autorExistente;
        this.idioma = dados.idioma().get(0);
        this.numeroDownloads = dados.numeroDownloads();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor.getId() +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }

}
