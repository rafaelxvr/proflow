package dev.ravel.proflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.ravel.proflow")
public class ProflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProflowApplication.class, args);
	}

}
