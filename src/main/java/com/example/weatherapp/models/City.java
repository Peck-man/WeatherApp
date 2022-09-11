package com.example.weatherapp.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor
public class City {
    @Id
    @SequenceGenerator(name = "cityGenerator", sequenceName = "CITY_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cityGenerator")
    private Integer id;
    @Column(nullable = false)
    private String cityName;
    @Column(nullable = false)
    private Integer lat;
    @Column(nullable = false)
    private Integer lon;

    public City(String cityName, Integer lat, Integer lon) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }
}
