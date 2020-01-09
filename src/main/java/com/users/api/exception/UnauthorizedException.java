package com.users.api.exception;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Não autorizado.");
    }
}
