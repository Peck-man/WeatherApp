package com.example.weatherapp.authentication;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("mysql")
@RequiredArgsConstructor
public class FakeUserDaoRepository implements UserDAO {

    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<UserDAOModel> selectUserDAOByUsername(String username) {
        return getUsers().stream().filter(userDAOModel -> username.equals(userDAOModel.getUsername()))
                .findFirst();
    }


    private List<UserDAOModel> getUsers() {
        return Lists.newArrayList(
                new UserDAOModel("John", passwordEncoder().encode("John1234"), new Set<GrantedAuthority>())
        )
    }
}
                /*userService.saveAppUser(new AppUser(,));
		userService.saveAppUser(new AppUser("Simon", "John2345"));
		userService.saveAppUser(new AppUser("Martin", "John3456"));
    }
}
