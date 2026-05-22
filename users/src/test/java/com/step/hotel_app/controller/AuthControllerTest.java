package com.step.hotel_app.controller;

import com.step.hotel_app.repository.UserRepository;
import com.step.hotel_app.views.UserReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.AutoConfigureDataMongo;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
@AutoConfigureDataMongo
class AuthControllerTest {
    @Autowired
    RestTestClient client;

    @Autowired
    UserRepository repo;
    @Test
    void registerUser() {
        client.post().uri("/api/users/register")
                .body(new UserReq("james","1234"))
                .exchange().expectStatus().isAccepted();
    }
    
    @Test
    void loginTest(){
        client.post().uri("/api/users/register")
                .body(new UserReq("jamie","1234"))
                .exchange().expectStatus().isAccepted();
        client.post().uri("/api/users/login").body(new UserReq("jamie","1234")).exchange().expectStatus().isOk();

    }
}