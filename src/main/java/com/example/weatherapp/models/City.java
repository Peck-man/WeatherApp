package com.example.weatherapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cityName;
    private Integer lat;
    private Integer lon;

    public City(String cityName, Integer lat, Integer lon) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }
}
