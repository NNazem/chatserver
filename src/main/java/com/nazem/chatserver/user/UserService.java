package com.nazem.chatserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void logUser(User user) {
        repository.updateUserStatus(user.getNickName(), Status.ONLINE);
    }

    public void disconnectUser(User user) {
        repository.updateUserStatus(user.getNickName(), Status.OFFLINE);
    }

    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }



    public boolean login(String usernameOrEmail, String password) throws Exception {
        User user = repository.findByEmailOrNickName(usernameOrEmail, usernameOrEmail);
        if(user != null && user.getPassword().equals(password)){
            return true;
        }

        throw new Exception("Invalid username/email or password");


    }

    public User signUpUser(User user) throws Exception {
        try {
            return repository.save(user);
        }catch (Exception e){
            throw new Exception("User with this email or username already exists");
        }
    }

    public List<User> findAllUsers(String nickName) {
        return repository.findAllByNickNameContains(nickName);
    }


}
