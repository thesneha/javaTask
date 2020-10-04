package com.java.task.repository;


import com.java.task.models.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Integer> {
     ResetPasswordToken findByUserId(Integer Userid);
}
