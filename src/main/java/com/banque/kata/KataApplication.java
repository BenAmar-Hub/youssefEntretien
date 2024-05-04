package com.banque.kata;

import com.banque.kata.dtos.ReleveDeCompteDTO;
import com.banque.kata.entities.Compte;
import com.banque.kata.entities.CompteCourant;
import com.banque.kata.entities.Livret;
import com.banque.kata.entities.Operation;
import com.banque.kata.repositories.OperationRepository;
import com.banque.kata.services.CompteBancaireService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class KataApplication {

	public static void main(String[] args) {
		SpringApplication.run(KataApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CompteBancaireService service){
		return args -> {
			System.out.println(service.releve("486cd9f2-1d51-4394-8ab6-0810eb03a31b", new Date()).toString());


		};
	}

}
