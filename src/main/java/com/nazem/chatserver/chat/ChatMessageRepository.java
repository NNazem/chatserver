package com.nazem.chatserver.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String chatId);

    ChatMessage findFirstBySenderIdAndRecipientIdOrderByTimestampDesc(String senderId, String recipientId);
}
