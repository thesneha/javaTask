package com.java.task.models;


import com.java.task.enums.RoleOfUser;
import com.java.task.enums.RoleOfUser;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name ="user")
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Integer id;
    private String userName;
    private String password;
    private String confirmPass;
    @Enumerated(EnumType.STRING)
    private RoleOfUser role;
    private String emailId;

}
