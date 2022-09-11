package com.example.weatherapp.authentication;

import com.example.weatherapp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.weatherapp.service.UserRoles.USER;

@Repository("mysql")
@RequiredArgsConstructor
public class FakeUserDaoRepository implements UserDAO {
    @Autowired
    private final UserRepo userRepo;


    @Override
    public Optional<UserDAOModel> selectUserDAOByUsername(String username) {
        return getUsers().stream().filter(userDAOModel -> username.equals(userDAOModel.getUsername()))
                .findFirst();
    }


    private List<UserDAOModel> getUsers() {
        ArrayList<UserDAOModel> arrayToReturn = new ArrayList<>();
        userRepo.findAll().forEach(appUser -> arrayToReturn.add(new UserDAOModel(appUser.getUsername(),
                appUser.getPassword(),
                USER.GetGrantedAuthorities(),
                true,
                true,
                true,
                true)));
        return arrayToReturn;
    }
}
