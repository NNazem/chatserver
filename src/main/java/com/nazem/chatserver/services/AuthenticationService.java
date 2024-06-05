package com.nazem.chatserver.services;

import com.nazem.chatserver.user.SignUpRequestDTO;
import com.nazem.chatserver.user.Status;
import com.nazem.chatserver.user.User;
import com.nazem.chatserver.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final AuthenticationManager authenticationManager;

    public User signup(SignUpRequestDTO input){
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setNickName(input.getNickName());
        user.setPassword(passwordService.hashPassword(input.getPassword()));
        user.setStatus(Status.OFFLINE);

        return userRepository.save(user);
    }

    public User authenticate(String usernameOrEmail, String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        password
                )
        );

        return userRepository.findByEmailOrNickName(usernameOrEmail, usernameOrEmail).orElseThrow();
    }
}
