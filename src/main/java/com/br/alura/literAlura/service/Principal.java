package com.br.alura.literAlura.service;


import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.InputMismatchException;
import com.br.alura.literAlura.model.ResultadoBusca;
import com.br.alura.literAlura.repository.AutorRepository;
import com.br.alura.literAlura.repository.LivroRepository;
import com.br.alura.literAlura.model.DadosLivro;
import com.br.alura.literAlura.model.Livro;
import com.br.alura.literAlura.model.Autor;
import com.br.alura.literAlura.model.DadosAutor;

@Service
public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados converteDados = new ConverteDados();
    private final String ENDPOINT = "https://gutendex.com/books/";

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    public void exibeMenu() {
        System.out.println("Bem-vindo ao LiterAlura!");
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                -----------------------------------------------------
                        1 - Buscar livro pelo título
                        2 - Listar livros registrados
                        3 - Listar autores
                        4 - Listar autores em determinado ano
                        5 - Listar livros em determinado idioma

                        0 - Sair    
                -----------------------------------------------------""");

            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número.");
                leitura.nextLine(); 
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutores();
                case 4 -> listarAutoresEmDeterminadoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        DadosLivro dados = obterDadosLivro();
        if (dados != null) {
            DadosAutor dadosAutor = dados.authors().get(0);
            System.out.println(dadosAutor);

            Livro livro = (Livro) livroRepository.findAll().stream()
                    .filter(l -> l.getTitulo().equalsIgnoreCase(dados.titulo()))
                    .findFirst()
                    .orElse(null);
            Autor autorExistente = autorRepository.findAll().stream()
                    .filter(a -> a.getNome().equalsIgnoreCase(dadosAutor.nome()))
                    .findFirst()
                    .orElse(null);

            if (autorExistente != null && livro == null ) {
                livro = new Livro(dados, autorExistente);
            } if(autorExistente != null && livro != null) {
                System.out.println(dadosAutor);
            } else {
                Autor novoAutor = new Autor(dadosAutor);
                autorRepository.save(novoAutor);
                livro = new Livro(dados, novoAutor);
            }

            try {
                livroRepository.save(livro);
                System.out.println(livro);
            } catch (Exception e) {
                System.out.println("""
                    ----------------------------------------------
                        O livro já consta no banco de dados!
                    ----------------------------------------------""");
            }
        }
    }

    private DadosLivro obterDadosLivro() {
        System.out.println("""
            -----------------------------------------------------
            Digite o título do livro que deseja buscar:
            -----------------------------------------------------""");

        String titulo = leitura.nextLine().toLowerCase().trim();
        String url = ENDPOINT + "?search=" + titulo.replace(" ", "+");

        String json = consumoApi.obterDados(url);
        ResultadoBusca resultado = converteDados.obterDadosJson(json, ResultadoBusca.class);

        return resultado.results().stream()
                .filter(l -> l.titulo().toLowerCase().contains(titulo))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Livro não encontrado.");
                    return null;
                });
    }

    private void listarAutoresEmDeterminadoAno() {
        try {
            System.out.println("""
                -----------------------------------------------------
                Digite o ano em que o autor estava vivo:
                -----------------------------------------------------""");

            int ano = leitura.nextInt();

            autorRepository.findAll().stream()
                    .filter(a -> a.getAnoNascimento() <= ano && a.getAnoFalecimento() >= ano)
                    .forEach(a -> System.out.println(a.toString()));

        } catch (InputMismatchException e) {
            System.out.println("Ano inválido.");
            leitura.nextLine();
        } catch (Exception e) {
            System.out.println("Sinto muito! Não consegui encontrar no banco de dados.");
        }
    }

    private void listarLivrosPorIdioma() {
        try {
            System.out.println("""
                -----------------------------------------------------
                Digite o idioma do livro que deseja buscar:

                pt - português
                en - inglês
                fr - francês
                es - espanhol

                -----------------------------------------------------""");

            String idioma = leitura.nextLine();

            livroRepository.findAll().stream()
                    .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                    .forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Não há livros nesse idioma.");
        }
    }

    private void listarAutores() {
        autorRepository.findAll().stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listarLivrosRegistrados() {
        livroRepository.findAll().stream()
                .sorted(Comparator.comparing(Livro::getNumeroDownloads).reversed())
                .forEach(System.out::println);
    }
}
