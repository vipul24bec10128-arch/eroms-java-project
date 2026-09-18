package com.eroms.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String userId;
    protected String username;
    protected String password;

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}