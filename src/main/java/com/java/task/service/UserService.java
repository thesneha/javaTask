package com.java.task.service;



import com.java.task.Response.LoginResponse;
import com.java.task.Response.UserResponse;
import com.java.task.models.User;
import com.java.task.request.ForgetPasswordRequest;
import com.java.task.request.LoginUserRequest;
import com.java.task.request.UpdatePasswordRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService  {

    public User saveUser(User user) throws Exception;

    public User fetchUserByEmailId(String emailId);

    LoginResponse loginUser(LoginUserRequest loginUserRequest) throws Exception;

    void sendPasswordActivateLink(ForgetPasswordRequest requestEntity, HttpServletRequest httpServletRequest) throws Exception;

    UserResponse validate(UpdatePasswordRequest updatePasswordRequest) throws Exception;

    UserResponse getUserProfile(HttpServletRequest httpServletRequest);
}
