package com.lfl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lfl.entity.Astreinte;
import com.lfl.entity.Event;
import com.lfl.entity.Notification;
import com.lfl.entity.RhFile;
import com.lfl.entity.RhFileValidation;
import com.lfl.repository.UserRepository;
import com.lfl.security.Role;
import com.lfl.security.Users;
import com.lfl.service.AccountService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@SpringBootApplication
public class Lfl76Application implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	AccountService userService;
	

	public static void main(String[] args) {
		SpringApplication.run(Lfl76Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/**Users u1 = new Users(); 
		u1.setUsername("admin");
		u1.setPassword("12345678");
		u1.setEmail("admin@lfl.org");
		u1.setFirstname("admin");
		u1.setLastname("admin");
		u1.setGender("M");
		u1.setOnboardingDate(new Date());
		u1.setCreatedAt(new Date());
		u1.setEnabled(true);
		userService.saveUser(u1);
		
		userService.saveRole(new Role("admin"));
		userService.saveRole(new Role("educator"));
		userService.saveRole(new Role("organisator"));
		userService.saveRole(new Role("apprentice"));
		userService.saveRole(new Role("inter"));
		userService.saveRole(new Role("monitor"));
		userService.saveRole(new Role("intendent"));
		userService.saveRole(new Role("cooker"));
		
		userService.addRoleToUser("admin", "admin");**/
	}

}
