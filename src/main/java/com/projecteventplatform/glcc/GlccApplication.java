package com.projecteventplatform.glcc;

import com.projecteventplatform.glcc.entities.Role;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GlccApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlccApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args ->{
			userService.createRole(new Role("ORGANIZER"));
			userService.createRole(new Role("PARTICIPANT"));
		} ;
	}

}
