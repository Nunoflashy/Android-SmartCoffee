package com.example.projetofinal_smartcoffee.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {

    }

    public UserNotFoundException(String error) {
        super(error);
    }

    public UserNotFoundException(String error, Throwable ex) {
        super(error, ex);
    }
}
