package com.example.Ecommerce.MasterThesis;
import com.example.Ecommerce.MasterThesis.controller.AuthController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@SpringBootApplication
public class MasterThesisApplication {







	public static void main(String[] args) {
		SpringApplication.run(MasterThesisApplication.class, args);
	}

}
