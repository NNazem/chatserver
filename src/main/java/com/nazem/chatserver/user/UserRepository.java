package com.nazem.chatserver.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface UserRepository  extends MongoRepository<User, String> {
    User findByNickName(String nickName);
    List<User> findAllByStatus(Status status);
    List<User> findAllByNickNameContains(String nickName);
    User findByEmailOrNickName(String email, String nickName);
    @Query("{'nickName': ?0}")
    @Update("{'$set':  {'status' :  ?1}}")
    void updateUserStatus(String nickName, Status status);

}