package com.java.task.Response;


import com.java.task.enums.RoleOfUser;
import com.java.task.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse  {

    private Integer id;
    private String userName;
    private RoleOfUser role;
    private String emailId;

    public  static UserResponse from(User user)
    {
        return UserResponse.builder()
                .id(user.getId())
                .emailId(user.getEmailId())
                .role(user.getRole())
                .userName(user.getUserName())
                .build();

    }
}
