package com.example.weatherapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    @SequenceGenerator(name = "cityGenerator", sequenceName = "CITY_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cityGenerator")
    private Integer id;
    @Column(nullable = false)
    private String cityName;
    @Column(nullable = false)
    private Float lat;
    @Column(nullable = false)
    private Float lon;

    public City(String cityName, float lat, float lon) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }
}
