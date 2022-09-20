package com.example.weatherapp.controllers;

import com.example.weatherapp.customExceptions.IncorrectValueException;
import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("cities")
    public List<City> getUsersCities(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token){
        List<City> cities;
        try {
            cities =  userService.getCitiesOfTheUser(token);
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized", e);
        }
        return cities ;
    }

    @PostMapping("cities/add")
    public String addCityToUser(@RequestHeader (value = HttpHeaders.AUTHORIZATION, required = false) String token,
                                @RequestBody City city){
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            city = userService.saveCity(city);
            return userService.addCityToUser(token, city);
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You didnt provide all of the data");
        } catch (IncorrectValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping("cities/delete")
    public String deleteCity(@RequestHeader (value = HttpHeaders.AUTHORIZATION, required = false) String token,
                             @RequestParam(required = false) Integer id){
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized");
        } else if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide an id");
        }
        return userService.deleteCityOfUser(token,id);
    }
    @GetMapping("cities/{id}")
    public String getInfo(@RequestHeader (value = HttpHeaders.AUTHORIZATION, required = false) String token,
                          @PathVariable Integer id){
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        return userService.weatherInfoRequest(token, id);
    }
}
