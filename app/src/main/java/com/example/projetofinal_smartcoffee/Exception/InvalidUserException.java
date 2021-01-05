package com.example.projetofinal_smartcoffee.Exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String error, Throwable ex) {
        super(error, ex);
    }

    public InvalidUserException(String error) {
        super(error);
    }
}
