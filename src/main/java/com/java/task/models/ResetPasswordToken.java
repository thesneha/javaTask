package com.java.task.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="resetPasswordToken")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Long sentAt;
    private Long usedAt;
    private String token;


}
