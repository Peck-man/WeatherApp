package com.example.weatherapp.service;

import com.example.weatherapp.customExceptions.IncorrectValueException;
import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;

import java.util.List;

public interface UserService {
    AppUser saveAppUser(AppUser appUser);
    City saveCity(City city) throws IncorrectValueException;
    String addCityToUser(String username, City city);
    String deleteCityOfUser(String token, Integer idOfCity);
    AppUser getUser(String username);
    List<City> getCitiesOfTheUser(String token);

    String getWeatherInfo(City city);
    String weatherInfoRequest(String token, Integer id);
    String userRegister(AppUser appUser);
}
