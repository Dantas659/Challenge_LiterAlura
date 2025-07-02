package com.br.alura.literAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.alura.literAlura.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
}
