package com.br.alura.literAlura.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.alura.literAlura.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, String> {   

}
