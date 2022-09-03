package com.example.weatherapp.service;

import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;
import com.example.weatherapp.repo.CityRepo;
import com.example.weatherapp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service  @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final CityRepo cityRepo;

    public UserServiceImpl(UserRepo userRepo, CityRepo cityRepo) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username);
        if(appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database");

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveAppUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getUsername());
        return userRepo.save(appUser);
    }

    @Override
    public City saveCity(City city) {
        log.info("Saving new user {} to the database", city.getCityName());

        return cityRepo.save(city);
    }

    @Override
    public void addCityToUser(String username, String cityName) {
        log.info("Adding city {} to user {}", cityName,username);
        AppUser appUser = userRepo.findByUsername(username);
        City city = cityRepo.findByCityName(cityName);
        appUser.getCities().add(city);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);

        return userRepo.findByUsername(username);
    }

    @Override
    public List<City> getCitiesOfTheUser(Long userId) {
        log.info("Fetching all cities of user with id {}", userId);

        return userRepo.findById(userId).get().getCities();
    }


}
