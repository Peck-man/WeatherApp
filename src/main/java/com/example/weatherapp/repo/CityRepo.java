package com.example.weatherapp.repo;

import com.example.weatherapp.models.City;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
    City findByCityName(String cityName);
    City findById(Integer id);



    boolean existsByCityName(String cityName);
}
