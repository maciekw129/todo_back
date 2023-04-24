package com.todo.todo.controllers;

import com.todo.todo.model.AuthenticationRequest;
import com.todo.todo.model.AuthenticationResponse;
import com.todo.todo.model.RegisterRequest;
import com.todo.todo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
            ) {
        System.out.println("asd");
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest
            ) {
        System.out.println("dssdfdfs");
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
