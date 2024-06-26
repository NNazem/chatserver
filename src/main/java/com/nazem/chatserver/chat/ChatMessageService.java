package com.nazem.chatserver.chat;

import com.nazem.chatserver.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage){
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId){
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }

    public ChatMessage findLastMessage(String senderId, String recipientId) {
        ChatMessage firstMessage =  repository.findFirstBySenderIdAndRecipientIdOrderByTimestampDesc(senderId, recipientId);
        ChatMessage secondMessage =  repository.findFirstBySenderIdAndRecipientIdOrderByTimestampDesc(recipientId, senderId);
        return firstMessage.getTimestamp().after(secondMessage.getTimestamp()) ? firstMessage : secondMessage;
    }
}
