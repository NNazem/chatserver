package com.nazem.chatserver.controllers;

import com.nazem.chatserver.services.AuthenticationService;
import com.nazem.chatserver.services.JwtService;
import com.nazem.chatserver.user.LoginRequestDTO;
import com.nazem.chatserver.user.LoginResponse;
import com.nazem.chatserver.user.SignUpRequestDTO;
import com.nazem.chatserver.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@CrossOrigin("*")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody SignUpRequestDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok("User correctly registered");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequestDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto.getUsernameOrEmail(), loginUserDto.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}