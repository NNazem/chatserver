package com.nazem.chatserver.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequestDTO {
    private String nickName;
    private String fullName;
    private Status status;
    private String password;
    private String email;
}
