package com.restapi.api.model.requests;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequests {
    String username;
    String password;

    public LoginRequests(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
