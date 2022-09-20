package com.example.weatherapp.repo;

import com.example.weatherapp.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    boolean existsByUsername(String username);

    @Override
    List<AppUser> findAll();
}
