package com.test5.model.requests;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequests {
    String username;
    String password;

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
