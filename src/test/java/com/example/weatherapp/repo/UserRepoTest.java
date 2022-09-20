package com.example.weatherapp.repo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRepoTest {
    @Autowired
    MockMvc mockMvc;
    //@MockBean
    //UserRepo userRepoTested;


    @Test
    void login() throws Exception {
        mockMvc.perform(post("https://localhost:8081/login")
                .content("""
                        {
                            "username": "Simon",
                            "password": "John1234"
                        }                  
                        """

        ).contentType("application/json")).andExpect(status().is(200));
    }
    // @Test
    // void itShouldCheckIfAppUserExistsByUsername() {
    //     // given
    //     AppUser appUser = new AppUser("I am", "TheBest");
    //     userRepoTested.save(appUser);
    //     // when
    //     boolean expected = userRepoTested.existsByUsername("I am");
    //     // then
    //     assertThat(expected).isTrue();
    // }
}