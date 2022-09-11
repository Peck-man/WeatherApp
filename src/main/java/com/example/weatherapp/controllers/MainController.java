package com.example.weatherapp.controllers;

import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLIntegrityConstraintViolationException;
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
            city = userService.saveCity(city);
            userService.addCityToUser(token, city);
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized", e);
        } catch (PropertyValueException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are missing one of the city's attribute", e);
        }
        return "City is add";
    }

}
