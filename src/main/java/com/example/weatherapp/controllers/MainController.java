package com.example.weatherapp.controllers;

import com.example.weatherapp.filter.JwtUsernameAndPasswordAuthFilter;
import com.example.weatherapp.jwt.UsernameAndPasswordAuthenticationRequest;
import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final UserService userService;


    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }
    @PostMapping("login")
    public String getHomePage(){
    return "logged in";
}
    @GetMapping("users")
    public String getAll(){
        return  "this page can reach everyone";
    }

    @GetMapping("admin")
    public String getAdminHomepage() {
        return "this page can reach only admin";
    }

    @GetMapping("users/cities")
    public List<City> getUsersCities(@RequestHeader (value = HttpHeaders.AUTHORIZATION, required = false) String token){
        List<City> cities;
        try {
           cities =  userService.getCitiesOfTheUser(token);
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized", e);
        }
        return cities ;
    }

    @PostMapping("users/cities/add")
    public String addCityToUser(@RequestHeader (value = HttpHeaders.AUTHORIZATION, required = false) String token,
                                @RequestBody City city){
        try {
            userService.addCityToUser(token, city.getCityName());
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized", e); //TODO: logic adding cities
        }
        return "City is add";
    }

}
