package com.RetourFacile.services;

import com.RetourFacile.dto.Request.LoginRequest;
import com.RetourFacile.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerUser(User user);
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
}
