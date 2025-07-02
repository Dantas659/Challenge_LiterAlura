package com.br.alura.literAlura.service;


import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.br.alura.literAlura.model.ResultadoBusca;
import com.br.alura.literAlura.repository.AutorRepository;
import com.br.alura.literAlura.repository.LivroRepository;
import com.br.alura.literAlura.model.DadosLivro;
import com.br.alura.literAlura.model.Livro;
import com.br.alura.literAlura.model.Autor;
import com.br.alura.literAlura.model.DadosAutor;


@Service
public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private String json;
    private final String ENDPOINT = "https://gutendex.com/books/";
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    public void exibeMenu() {
        System.out.println("Bem-vindo ao LiterAlura!");
        var opcao = -1; 

        while (opcao != 0) {
            var menu = """
            -----------------------------------------------------
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores em determinado ano
                    5 - Listar livros em determinado idioma

                    0 - Sair    
            -----------------------------------------------------                             
                    """;

            System.out.println(menu);
            try {
            opcao = leitura.nextInt();
            leitura.nextLine();
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida!");
                return;
            }
            

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarLivrosPorIdioma();
                    break;
               
                case 0:
                    System.out.println("Saindo...");
                    break;
                default: 
                    System.out.println("Opção inválida");
            }
        }
    }

   private void buscarLivroPorTitulo() {
    DadosLivro dados = obterDadosLivro();
    if (dados != null) {
        DadosAutor dadosAutor = dados.authors().get(0);
        System.out.println(dadosAutor);
                Livro livro;
        Autor autorExistente = autorRepository.findAll().stream()
                    .filter(a -> a.getNome().equalsIgnoreCase(dadosAutor.nome()))
                    .findFirst()
                    .orElse(null);
        if(autorExistente != null) {
            livro = new Livro(dados, autorExistente);
            if(autorExistente != null) {
                livro = new Livro(dados, autorExistente);
            }
        } else {
            Autor novoAutor = new Autor(dadosAutor);
            livro = new Livro(dados, novoAutor);
            autorRepository.save(novoAutor);
        }
        try {
            livroRepository.save(livro);
            System.out.println(livro);
        } catch (Exception e) {
            System.out.println("""
            ----------------------------------------------
                O livro já consta no banco de dados!
            ----------------------------------------------
                """);
        }
    }


}


    private DadosLivro obterDadosLivro() {
    System.out.print("""
            -----------------------------------------------------
            Digite o título do livro que deseja buscar:
            -----------------------------------------------------
            """);
    String titulo = leitura.nextLine().toLowerCase().trim();
    String url = ENDPOINT + "?search=" + titulo.replace(" ", "+");
    json = consumoApi.obterDados(url);
    var dadosLivro = converteDados.obterDadosJson(json, ResultadoBusca.class);
    Optional<DadosLivro> dados = dadosLivro.results().stream()
            .filter(l -> l.titulo().toLowerCase().contains(titulo))
            .findFirst();
    if (dados.isPresent()) {
        return dados.get();
    } else {
        System.out.println("Livro não encontrado.");
        return null;
    }
}

    private void listarLivrosPorIdioma() {
    
    }


    private void listarAutores() {

    }


    private void listarLivrosRegistrados() {
      
    }

    
}

