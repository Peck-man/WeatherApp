package com.example.weatherapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class AppUser {
    @Id
    @SequenceGenerator(name = "appGenerator", sequenceName = "APPUSER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appGenerator")
    private Long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private List<City> cities;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.cities = new ArrayList<>();
    }
}
