package com.example.weatherapp.controllers;

import com.example.weatherapp.filter.JwtUsernameAndPasswordAuthFilter;
import com.example.weatherapp.jwt.UsernameAndPasswordAuthenticationRequest;
import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    @GetMapping("login")
    public String getLoginPage(){

        return "login";
    }
    @PostMapping("login")
    @ResponseBody
    public String getHomePage(){

    return "logged in";
}
    @GetMapping("users")
    @ResponseBody
    public String getAll(){
        return  "this page can reach everyone";
    }

    @GetMapping("admin")
    @ResponseBody
    public String getAdminHomepage() {
        return "this page can reach only admin";
    }

    @GetMapping("users/cities")
    @ResponseBody
    public List<City> getUsersCities(@RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        System.out.println(token);
        return  userService.getCitiesOfTheUser(token);
    }

}
