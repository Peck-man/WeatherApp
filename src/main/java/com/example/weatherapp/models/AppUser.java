package com.example.weatherapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<City> cities;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.cities = new ArrayList<>();
    }
}
