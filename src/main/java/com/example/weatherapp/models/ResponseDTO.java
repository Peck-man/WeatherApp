package com.example.weatherapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {

    private String name;
    private MainDTO main;
}
