package com.users.api.exception;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException() {
        super("E-mail já existente");
    }
}
