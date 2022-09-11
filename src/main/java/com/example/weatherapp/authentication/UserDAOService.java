package com.example.weatherapp.authentication;

import com.example.weatherapp.models.AppUser;
import com.example.weatherapp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class UserDAOService implements UserDetailsService {

    private final UserDAO userDAO;
    private final UserRepo userRepo;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.selectUserDAOByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
