package com.users.api.exception;

public class EmailOrPasswordIncorrectException extends Exception {
    public EmailOrPasswordIncorrectException() {
        super("Email ou senha incorretos.");
    }
}
