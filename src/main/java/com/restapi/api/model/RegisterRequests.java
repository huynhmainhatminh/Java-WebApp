package com.restapi.api.model;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequests {
    String fullName;
    String username;
    String password;
    String confirmPassword;


    @Override
    public String toString() {
        return "Register{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
