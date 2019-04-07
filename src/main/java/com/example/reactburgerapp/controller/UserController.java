package com.example.reactburgerapp.controller;

import com.example.reactburgerapp.model.RestApiAnswer;
import com.example.reactburgerapp.model.User;
import com.example.reactburgerapp.service.MongoUserService;
import com.example.reactburgerapp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class UserController {

    private final MongoUserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public UserController(MongoUserService userService, AuthenticationManager authenticationManager,
                          TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RestApiAnswer> registerUser(@RequestBody User user) {
        RestApiAnswer answer = new RestApiAnswer();
        answer.setUserEmail(user.getEmail());
        if (userService.getByEmail(user.getEmail()) != null) {
            answer.setError("User e-mail already exists");
            return ResponseEntity.ok(answer);
        }
        User savedUser = userService.saveUser(user);
        String token = tokenService.createToken(savedUser.getId(), savedUser.getEmail());
        answer.setUserEmail(savedUser.getEmail());
        answer.setUserId(savedUser.getId());
        answer.setToken(token);
        return ResponseEntity.ok(answer);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            User userFound = userService.getByEmail(user.getEmail());
            String token = tokenService.createToken(userFound.getId(), userFound.getEmail());
            RestApiAnswer answer = new RestApiAnswer();
            answer.setToken(token);
            answer.setUserId(userFound.getId());
            answer.setUserEmail(user.getEmail());
            return ResponseEntity.ok(answer);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
