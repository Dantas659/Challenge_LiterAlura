package com.br.alura.literAlura;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.br.alura.literAlura.service.Principal;

@EnableJpaRepositories
@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { 
		principal.exibeMenu();
		
	}
}
