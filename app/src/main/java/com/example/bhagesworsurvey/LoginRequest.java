package com.example.bhagesworsurvey;
public class LoginRequest {
    private String User_Id;
    private String Password;

    public LoginRequest(String user_Id, String password) {
        User_Id = user_Id;
        Password = password;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public String getPassword() {
        return Password;
    }
}
