package com.nazem.chatserver.chatroom;

import com.nazem.chatserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/searchConversation")
    public ResponseEntity<List<String>> findAllConversation(@RequestParam String nickName) {
        return ResponseEntity.ok(chatRoomService.findConversations(nickName));
    }
}
