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
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class WeatherAppApplication implements CommandLineRunner {

	private UserService userService;
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		userService.saveAppUser(new AppUser("John", passwordEncoder.encode("John1234")));
		userService.saveAppUser(new AppUser("Simon", passwordEncoder.encode("John1234")));
		userService.saveAppUser(new AppUser("Emily", passwordEncoder.encode("John1234")));
		userService.saveCity(new City("Benešov", 49.78f, 14.68f));
		userService.saveCity(new City("Strašín", 50.00f, 14.70f));
		userService.saveCity(new City("Středokluky", 50.13f, 14.23f));
	}
}
