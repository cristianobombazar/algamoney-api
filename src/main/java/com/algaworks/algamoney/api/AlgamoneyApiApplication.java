package com.algaworks.algamoney.api;

import com.algaworks.algamoney.api.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamoneyApiApplication.class, args);
		System.out.println(Util.passwordGenerate("admin"));
	}
}
