package com.asif.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class LoginDto {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}