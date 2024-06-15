package com.alura.literatura;

import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner
{
	@Autowired // Inyección de dependencias para LibroRepository
	private LibroRepository libroRepository;
	@Autowired // Inyección de dependencias para AutorRepository
	private AutorRepository autorRepository;

	// Método principal que inicia la aplicación Spring Boot
	public static void main(String[] args)
	{
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	// Método que se ejecuta después de iniciar la aplicación Spring Boot
	@Override
	public void run(String... args) throws Exception
	{
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.menu();
	}
}
