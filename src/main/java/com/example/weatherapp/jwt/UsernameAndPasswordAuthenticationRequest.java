package com.example.weatherapp.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {
    private String username, password;

}
