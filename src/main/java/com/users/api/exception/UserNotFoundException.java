package com.users.api.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
