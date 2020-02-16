package com.example.doshop.domain;

import java.util.Objects;

public class Usuario {
    private String userId;
    private String userName;

    public Usuario(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
