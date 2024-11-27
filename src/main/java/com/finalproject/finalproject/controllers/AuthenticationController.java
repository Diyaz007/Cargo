package com.finalproject.finalproject.controllers;


import com.finalproject.finalproject.Exceptions.EmailTakenException;
import com.finalproject.finalproject.Exceptions.LoginException;
import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.LoginRequest;
import com.finalproject.finalproject.dto.LoginResponse;
import com.finalproject.finalproject.dto.RegisterRequest;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.services.AuthenticationService;
import com.finalproject.finalproject.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerUserDto) {
        try {
            Users registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        } catch (SignUpException exception) {
            // Логирование ошибок
            Map<String, Integer> errors = exception.getErrors();
            System.out.println("Validation errors: " + errors);

            // Возврат детализированного ответа с ошибками
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } catch (EmailTakenException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(409, "Email already taken"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequest loginUserDto) {
        try {
            Users authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }catch (LoginException exception) {
            Map<String, Integer> errors = exception.getErrors();
            System.out.println("Login errors: " + errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }
}
