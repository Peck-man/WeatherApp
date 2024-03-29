package com.example.weatherapp.service;

import com.example.weatherapp.customExceptions.IncorrectValueException;
import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.models.City;
import com.example.weatherapp.models.ResponseDTO;
import com.example.weatherapp.repo.CityRepo;
import com.example.weatherapp.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service  @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final CityRepo cityRepo;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepo userRepo, CityRepo cityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String userRegister(AppUser appUser){
        if (userRepo.existsByUsername(appUser.getUsername())){
            return "We already have user with this username";
        }
        userRepo.save(new AppUser(appUser.getUsername(), passwordEncoder.encode(appUser.getPassword())));
        return "Successfully registered";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username);
        if(appUser == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveAppUser(AppUser appUser) {
        return userRepo.save(appUser);
    }

    @Override
    public City saveCity(City city) throws IncorrectValueException {
        if (city.getLat()< -90 || city.getLat() > 90 || city.getLon()< -180 || city.getLon() > 180){
            throw new IncorrectValueException("You provided values in bad range");
        }
        if (cityRepo.existsByCityName(city.getCityName())) {
            return cityRepo.findByCityName(city.getCityName());
        }
        cityRepo.save(city);
        return city;
    }

    @Override
    public String addCityToUser(String token, City city) {
        AppUser appUser = userRepo.findByUsername(getUsernameFromToken(token));
        if (!hasUserCity(appUser, city)){
            appUser.getCities().add(city);
            return "City is add";
        }
        return "City is already in the list";
    }

    public String deleteCityOfUser(String token, Integer id){
        AppUser appUser = userRepo.findByUsername(getUsernameFromToken(token));
        List<City> userCities = appUser.getCities();
        for (int i = 0; i < userCities.size(); i++) {
            if (Objects.equals(userCities.get(i).getId(), id)) {
                userCities.remove(i);
                return "Successfully removed";
            }
        }
        return "You dont have city with this id";
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
    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

    public String weatherInfoRequest(String token, Integer id){
        AppUser appUser = userRepo.findByUsername(getUsernameFromToken(token));
        City city = cityRepo.findById(id);
        if (city == null || !hasUserCity(appUser, city) ){
            return "You dont have city with this id";
        }
        return getWeatherInfo(city);
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
                .setSigningKey(Keys.hmacShaKeyFor(System.getenv("SECRET_KEY").getBytes()))
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body.getSubject();
    }

    public String getWeatherInfo(City city){
        WebClient client = WebClient.create();
        ClientResponse responseJson = client.get()
                .uri("https://api.openweathermap.org/data/2.5/weather?lat="+city.getLat()+"&lon="+city.getLon()+"&appid=" + System.getenv("WEATHER_KEY"))
                .exchange()
                .block();
        ResponseDTO responseDTO = responseJson
                .bodyToMono(ResponseDTO.class)
                .block();
        System.out.println(responseJson);
        String currentTemp = String.valueOf(responseDTO.getMain().getTemp()-273);
        return "In " +  responseDTO.getName() + " is currently " + currentTemp + " celsia.";
    }


}
