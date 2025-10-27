package com.restapi.api.model.response;
import lombok.Getter;

@Getter
public class ResponseAPI {
    private final boolean success;
    private final String message;

    public ResponseAPI(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
