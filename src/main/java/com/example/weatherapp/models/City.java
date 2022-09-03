package com.example.weatherapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity @Data @AllArgsConstructor @RequiredArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cityName;
    private Integer lat;
    private Integer lon;
    @ManyToMany
    private ArrayList<AppUser> userList = new ArrayList<>();
}
