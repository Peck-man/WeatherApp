package com.example.weatherapp.controllers;
import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
private final UserService userService;

    @PostMapping("register")
    public String registerUser(@RequestBody AppUser appUser){
        if (appUser == null || appUser.getPassword() == null || appUser.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You didnt provide all of the data");
        }
        return userService.userRegister(appUser);
    }
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
