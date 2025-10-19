package com.test5.model.response;

import lombok.Getter;

@Getter
public class RegisterResponse {

    private final boolean success;
    private final String message;

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
