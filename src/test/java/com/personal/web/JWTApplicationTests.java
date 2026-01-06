package com.personal.web;


import com.personal.web.entities.User;
import com.personal.web.services.JWTService;
import org.junit.jupiter.api.Test;

public class JWTApplicationTests {
    private final JWTService jwtService = new JWTService();

    @Test
    void contextLoads(){
        User user = new User(2L, "zulfan", "123akubro");

        String token = jwtService.createToke(user);

        System.out.println("Create token: " + token);

        Long id = jwtService.generateUserIdFromToken(token);

        System.out.println("Generate id from token: " + id);
    }
}
