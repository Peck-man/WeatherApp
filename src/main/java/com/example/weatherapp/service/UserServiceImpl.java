package com.example.weatherapp.service;

import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;
import com.example.weatherapp.repo.CityRepo;
import com.example.weatherapp.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

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
        if (cityRepo.existsByCityName(city.getCityName())) {
            return cityRepo.findByCityName(city.getCityName());
        }
        cityRepo.save(city);
        return city;
    }

    @Override
    public void addCityToUser(String token, City city) {
        AppUser appUser = userRepo.findByUsername(getUsernameFromToken(token));
        if (!hasUserCity(appUser, city)){
            appUser.getCities().add(city);
        }


    }

    public boolean hasUserCity(AppUser appUser, City city){
        boolean hasIt = false;
        for (int i = 0; i < appUser.getCities().size(); i++) {
            if (Objects.equals(appUser.getCities().get(i).getId(), city.getId())) {
                hasIt = true;
                break;
            }
        }
        return hasIt;
    }

    @Override
    public AppUser getUser(String username) {

        return userRepo.findByUsername(username);
    }

    @Override
    public List<City> getCitiesOfTheUser(String token) {
        String username = getUsernameFromToken(token);
        return userRepo.findByUsername(username).getCities();
    }

    public String getUsernameFromToken(String token) {
         token = token.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor("hellohellohellohellohellohellohellohellohellohellohellohellohello".getBytes()))
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body.getSubject();
    }

}
