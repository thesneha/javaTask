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
public class UserToken {

    private Integer id;
    private String userName;
    private RoleOfUser role;
    private String emailId;
    private Long createdAt;

    public  static UserToken from(User user)
    {
        return UserToken.builder()
                .id(user.getId())
                .emailId(user.getEmailId())
                .role(user.getRole())
                .userName(user.getUserName())
                .createdAt(System.currentTimeMillis()/1000)
                .build();
    }
}
