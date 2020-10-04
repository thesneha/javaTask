package com.java.task.controllers;

import com.java.task.Response.LoginResponse;
import com.java.task.Response.UserResponse;
import com.java.task.configuration.Authorize;
import com.java.task.models.User;
import com.java.task.request.ForgetPasswordRequest;
import com.java.task.request.LoginUserRequest;
import com.java.task.request.UpdatePasswordRequest;
import com.java.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/javaTask")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value ="/registerUser", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) throws Exception
    {
         return userService.saveUser(user);
    }

    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public LoginResponse loginUser(@RequestBody LoginUserRequest loginUserRequest) throws Exception {

        LoginResponse response = userService.loginUser(loginUserRequest);
        return response;
    }

    @Authorize
    @RequestMapping(value ="/getUserProfile", method = RequestMethod.GET)
    public UserResponse loginUser( HttpServletRequest httpServletRequest) throws Exception {

        UserResponse response = userService.getUserProfile(httpServletRequest);
        return response;
    }

    @RequestMapping(value ="/forgetPassword", method = RequestMethod.POST)
    public String forgetPassword(@RequestBody ForgetPasswordRequest requestEntity, HttpServletRequest httpServletRequest) throws Exception {
        userService.sendPasswordActivateLink(requestEntity,httpServletRequest);
        return "Success";
    }

    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public UserResponse validate(@RequestBody UpdatePasswordRequest updatePasswordRequest) throws Exception {
       return userService.validate(updatePasswordRequest);
    }

}
