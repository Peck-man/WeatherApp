package com.example.weatherapp.controllers;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MainController {


    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }
    @PostMapping("login")
    public String getHomePage(){
    return "logged in";
}
    @GetMapping("users")
    public String getAll(){
        return  "this page can reach everyone";
    }
    @GetMapping("admin")
    public String getAdminHomepage() {
        return "this page can reach only admin";
    }

}
