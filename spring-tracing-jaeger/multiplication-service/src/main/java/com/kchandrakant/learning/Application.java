package com.kchandrakant.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@GetMapping("/multiply/{number}")
	public int multiply(@PathVariable int number) {
		return number * number;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
