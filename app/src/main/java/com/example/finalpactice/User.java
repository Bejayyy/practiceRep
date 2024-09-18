package com.example.finalpactice;

public class User {
    private String username;
    private String password;
    private String email;

    //Constructor for Registration
    public User (String username,String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


    // Constructor for login (email and password only)
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}