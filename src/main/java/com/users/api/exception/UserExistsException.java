package com.users.api.exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User with informed email already exists.");
    }
}
