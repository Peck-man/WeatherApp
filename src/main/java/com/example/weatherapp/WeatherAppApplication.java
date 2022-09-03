package com.example.weatherapp;

import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class WeatherAppApplication implements CommandLineRunner {
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		userService.saveAppUser(new AppUser("John", "John1234"));
		userService.saveAppUser(new AppUser("Simon", "John2345"));
		userService.saveAppUser(new AppUser("Martin", "John3456"));
		userService.saveCity(new City("Benešov", 50, 60));
		userService.saveCity(new City("Vlašim", 70, 80));
		userService.saveCity(new City("Bystřice", 30, 40));
		userService.addCityToUser("John", "Benešov");
		userService.addCityToUser("Simon", "Vlašim");
		userService.addCityToUser("Simon", "Bystřice");
	}
}
