package com.example.weatherapp.authentication;

import java.util.Optional;

public interface UserDAO {

    Optional<UserDAOModel> selectUserDAOByUsername(String username);
}
