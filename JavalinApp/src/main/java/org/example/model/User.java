package org.example.model;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.userId = id;
        this.userName = name;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}