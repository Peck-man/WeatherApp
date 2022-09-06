package com.example.weatherapp.controllers;

import com.example.weatherapp.models.City;
import com.example.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
