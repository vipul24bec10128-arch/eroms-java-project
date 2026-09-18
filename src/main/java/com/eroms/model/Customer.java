package com.eroms.model;

public class Customer extends User {
    private static final long serialVersionUID = 1L;
    private String email;

    public Customer(String userId, String username, String password, String email) {
        super(userId, username, password);
        this.email = email;
    }

    public String getEmail() { return email; }
}