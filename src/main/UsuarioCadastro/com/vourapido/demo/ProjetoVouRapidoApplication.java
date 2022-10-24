package com.vourapido.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.vourapido.model.entity"})
@EnableJpaRepositories({"com.vourapido.repositories"})
@ComponentScan(basePackages = {"com.vourapido.controller", "com.vourapido.services",
	"com.vourapido.services.impl","com.vourapido.security"})

@SpringBootApplication
public class ProjetoVouRapidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoVouRapidoApplication.class, args);
	}

}
