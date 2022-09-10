package com.example.weatherapp.security;

import com.example.weatherapp.filter.JwtUsernameAndPasswordAuthFilter;
import com.example.weatherapp.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.weatherapp.service.UserRoles.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity @RequiredArgsConstructor @Configuration @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     private final UserDetailsService userDetailsService;
     private final PasswordEncoder passwordEncoder;
         @Override
         protected void configure(HttpSecurity http) throws Exception {
             http.
                     csrf().disable()
                     .authorizeRequests()
                     .and()
                     .formLogin()
                     .loginPage("/login")
                     .permitAll()
                     .and()
                     .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager()))
                     .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthFilter.class)
                     .authorizeRequests()
                     .antMatchers("/admin/**")
                     .hasRole(ADMIN.name())
                     .antMatchers("/login/**")
                     .permitAll()
                     .anyRequest()
                     .authenticated();
         }

         @Override
         protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
         }


 }