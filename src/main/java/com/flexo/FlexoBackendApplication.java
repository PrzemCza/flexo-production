package com.flexo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class FlexoBackendApplication {

	public static void main(String[] args) {
		System.out.println("Nowa wersja działa!");
		SpringApplication.run(FlexoBackendApplication.class, args);
	}

}
