package com.example.bhagesworsurvey;

import java.util.List;
public class LoginResponse {
    private int success;
    private String message;
    private List<User> result;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getResult() {
        return result;
    }
}

