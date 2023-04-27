package com.todo.todo.services;

import com.todo.todo.model.AuthenticationRequest;
import com.todo.todo.model.AuthenticationResponse;
import com.todo.todo.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    boolean isUserAlreadyExists(String email);
}
