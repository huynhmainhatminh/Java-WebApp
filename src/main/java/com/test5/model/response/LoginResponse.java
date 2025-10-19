package com.test5.model.response;


import lombok.Getter;

@Getter
public class LoginResponse {


    private final boolean success;
    private final String message;
    private String token;


    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public LoginResponse(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

}
