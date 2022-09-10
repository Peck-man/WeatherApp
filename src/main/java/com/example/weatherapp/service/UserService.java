package com.example.weatherapp.service;

import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface UserService {
    AppUser saveAppUser(AppUser appUser);
    City saveCity(City city);
    void addCityToUser(String username, String cityName);
    AppUser getUser(String username);
    List<City> getCitiesOfTheUser(String token);
}
