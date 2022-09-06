package com.example.weatherapp.authentication;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.weatherapp.service.UserRoles.ADMIN;
import static com.example.weatherapp.service.UserRoles.USER;

@Repository("mysql")
@RequiredArgsConstructor
public class FakeUserDaoRepository implements UserDAO {
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<UserDAOModel> selectUserDAOByUsername(String username) {
        return getUsers().stream().filter(userDAOModel -> username.equals(userDAOModel.getUsername()))
                .findFirst();
    }


    private List<UserDAOModel> getUsers() {
        return Lists.newArrayList(
                new UserDAOModel("Simon",
                        passwordEncoder.encode("John1234"),
                        ADMIN.GetGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new UserDAOModel("Emily",
                        passwordEncoder.encode("John1234"),
                        USER.GetGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new UserDAOModel("Natalia",
                        passwordEncoder.encode("John1234"),
                        USER.GetGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
