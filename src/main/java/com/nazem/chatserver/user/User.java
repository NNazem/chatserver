package com.nazem.chatserver.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {
    @Indexed(unique = true)
    private String nickName;
    private String fullName;
    private Status status;
    private String password;
    @Indexed(unique = true)
    private String email;
}