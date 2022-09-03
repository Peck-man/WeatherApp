package com.example.weatherapp.controllers;

import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<City>> getAll(){
        return ResponseEntity.ok().body(userService.getCitiesOfTheUser(1L));
    }

}
