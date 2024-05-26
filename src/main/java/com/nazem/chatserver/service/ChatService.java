package com.nazem.chatserver.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatService {

    private Set<String> connectedUsers = new HashSet<>();


    public void addConnectedUser(String username) {
        connectedUsers.add(username);
    }

    public void removeConnectedUser(String username) {
        connectedUsers.remove(username);
    }

    public Set<String> getConnectedUsers() {
        return connectedUsers;
    }
}
