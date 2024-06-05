package com.nazem.chatserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public void logUser(User user) {
        repository.updateUserStatus(user.getNickName(), Status.ONLINE);
    }

    public void disconnectUser(User user) {
        repository.updateUserStatus(user.getNickName(), Status.OFFLINE);
    }

    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    public User login(String usernameOrEmail, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        password
                )
        );
        return userRepository.findByEmailOrNickName(usernameOrEmail, usernameOrEmail).orElseThrow();
    }

    @Transactional
    public User signUpUser(SignUpRequestDTO signUpRequestDTO) throws Exception {
        User user = new User();
        user.setNickName(signUpRequestDTO.getNickName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setStatus(Status.OFFLINE);
        user.setFullName(signUpRequestDTO.getFullName());

        return userRepository.save(user);

    }


    public List<String> findAllUsers(String nickName) {
        List<User> users =  repository.findAllByNickNameContains(nickName);
        return users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
    }


}
