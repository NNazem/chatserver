package com.nazem.chatserver.controller;

import com.nazem.chatserver.model.Message;
import com.nazem.chatserver.model.Status;
import com.nazem.chatserver.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@AllArgsConstructor
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private ChatService service;


    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    private Message receivePublicMessage(@Payload Message message) {
        if(message.getStatus() == Status.JOIN){
            service.addConnectedUser(message.getSenderName());
        } else if(message.getStatus() == Status.LEAVE){
            service.removeConnectedUser(message.getSenderName());
        }
        return message;
    }

    @MessageMapping("/private-message") // /app/private
    @SendTo("/user/{receiverName}")
    private Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/David/private
        return message;
    }

    @MessageMapping("/connected-users") // /app/connected-users
    @SendTo("/app/connected-users")
    public Set<String> getConnectedUsers() {
        return service.getConnectedUsers();
    }

}
