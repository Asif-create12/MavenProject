package com.asif.exception;

public class UserExists extends RuntimeException {
    public UserExists(String msg) {
        super(msg);
    }

    public UserExists() {
    }
}
