package com.step.hotel_app.controller;

import com.step.hotel_app.service.AppUserDetailService;
import com.step.hotel_app.service.JWTService;
import com.step.hotel_app.views.JWTTokenView;
import com.step.hotel_app.views.UserReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final AppUserDetailService appUserDetailService;
    private final JWTService jwtService;

    public AuthController(AppUserDetailService appUserDetailService, JWTService jwtService) {
        this.appUserDetailService = appUserDetailService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserReq user) {
        boolean success = appUserDetailService.register(user);
        return success ? ResponseEntity.accepted().build() : ResponseEntity.ok("not registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserReq user) {
        try {
            System.out.println(user.username());

            UserDetails userDetails = appUserDetailService.loadUserByUsername(user.username(),user.password());
            System.out.println(userDetails);
            String username = userDetails.getUsername();
            String token = jwtService.generateTokens(username);
            return ResponseEntity.ok(new JWTTokenView(token));

        } catch (Throwable e) {
            System.out.println(e.getMessage());
            return  ResponseEntity.notFound().build();
        }
    }
}
