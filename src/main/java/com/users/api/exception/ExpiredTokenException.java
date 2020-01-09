package com.users.api.exception;

public class ExpiredTokenException extends Exception {
    public ExpiredTokenException() {
        super("Token expirado.");
    }
}
