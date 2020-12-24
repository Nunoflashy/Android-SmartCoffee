package com.example.projetofinal_smartcoffee.Exception;

public class ClienteExistsException extends RuntimeException {
    public ClienteExistsException() {
        super("O cliente já existe na base de dados!");
    }
}
