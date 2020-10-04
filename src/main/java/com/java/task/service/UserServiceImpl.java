package com.java.task.service;

import com.java.task.Response.LoginResponse;
import com.java.task.Response.TokenResponse;
import com.java.task.Response.UserResponse;
import com.java.task.Response.UserToken;
import com.java.task.enums.RoleOfUser;
import com.java.task.models.ResetPasswordToken;
import com.java.task.models.User;
import com.java.task.repository.ResetPasswordTokenRepository;
import com.java.task.repository.UserRepository;
import com.java.task.request.ForgetPasswordRequest;
import com.java.task.request.LoginUserRequest;
import com.java.task.request.UpdatePasswordRequest;
import com.java.task.utils.EncodeUtil;
import com.java.task.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private  EmailService emailService;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Override
    public User saveUser(User user) throws Exception {
        String tempEmailId= user.getEmailId();
        if (tempEmailId!=null&&!"".equals(tempEmailId))
        {
            User userObj= userService.fetchUserByEmailId(tempEmailId);
            if (userObj!=null)
            {
                throw new Exception("user with"+tempEmailId+" is already present");
            }
        }
        if (!(user.getPassword().equals(user.getConfirmPass())))
        {
            throw new Exception("pass is not equal to confirm pass");
        }

        return userRepository.save(user);
    }

    @Override
    public User fetchUserByEmailId(String emailId)
    {
        return userRepository.findByEmailId(emailId);
    }

    @Override
    public LoginResponse loginUser(LoginUserRequest loginUserRequest) throws Exception {
      User user=   fetchUserByEmailId(loginUserRequest.getEmailId());
      UserToken userToken = UserToken.from(user);
      String response= JsonUtil.getJsonStringFromObject(userToken);

    return LoginResponse.builder()
              .userResponse(UserResponse.from(user))
              .tokenResponse(TokenResponse.builder()
                      .accessToken(EncodeUtil.encrypt(response,JsonUtil.secretKey))
                      .expiresIn(30*60)
                      .build())
              .build();
    }

    @Override
    public UserResponse getUserProfile(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        UserToken userToken = EncodeUtil.getUserTokenByAuthorizationHeader(token);
        String email=userToken.getEmailId();
        User user= userRepository.findByEmailId(email);
        UserResponse userResponse= UserResponse.from(user);
        return userResponse;
    }

    @Override
    public void sendPasswordActivateLink(ForgetPasswordRequest requestEntity, HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader("Authorization");

        if (StringUtils.isEmpty(token))
        {
            throw new Exception("Authentication failed");
        }
        UserToken userToken = EncodeUtil.getUserTokenByAuthorizationHeader(token);

        if (!RoleOfUser.ADMIN.equals(userToken.getRole()))
        {
            throw new Exception("User is not admin");
        }
        String email=requestEntity.getEmailId();
        User user=userRepository.findByEmailId(email);
        if (user==null)
        {
            throw new Exception("User not found");
        }

       ResetPasswordToken resetPasswordToken=  ResetPasswordToken.builder()
                .userId(user.getId())
                .sentAt(System.currentTimeMillis()/1000)
                .token(EncodeUtil.generateSecret())
                .build();
        resetPasswordTokenRepository.save(resetPasswordToken);
        emailService.sendText(user,resetPasswordToken.getToken());
    }

    @Override
    public UserResponse validate(UpdatePasswordRequest updatePasswordRequest) throws Exception {

        if (StringUtils.isEmpty(updatePasswordRequest.getPassword()))
        {
            throw  new Exception("invalid password");
        }
        byte[] byteArray=EncodeUtil.DecodeBase64(updatePasswordRequest.getEncriptedEmail());
        String email = new String(byteArray);
        User user= userRepository.findByEmailId(email);
        if (user==null)
        {
            throw new Exception("user not found");
        }

      ResetPasswordToken resetPasswordToken= resetPasswordTokenRepository.findByUserId(user.getId());
        if (resetPasswordToken==null)
        {
            throw new Exception("Authentication failed");
        }

        if (!resetPasswordToken.getToken().equals(updatePasswordRequest.getToken()))
        {
            throw new Exception("Invalid token");
        }
        resetPasswordToken.setUsedAt(System.currentTimeMillis()/1000);
        resetPasswordTokenRepository.save(resetPasswordToken);
        user.setPassword(updatePasswordRequest.getPassword());
        userRepository.save(user);
        return UserResponse.from(user);
    }

}
