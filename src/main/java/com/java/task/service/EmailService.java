package com.java.task.service;


import com.java.task.models.User;

public interface EmailService {

    void sendText(User user, String token) throws Exception;
}



