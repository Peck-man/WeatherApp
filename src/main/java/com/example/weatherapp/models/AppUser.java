package com.example.weatherapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class AppUser {
    @Id
    @SequenceGenerator(name = "appUserGenerator", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appUserGenerator")
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
