package com.java.task.enums;

public enum RoleOfUser {
    USER("user"),
    ADMIN("admin");

    private String userRole;

    private RoleOfUser(String userRole)
    {
       this.userRole=userRole;
    }
}
