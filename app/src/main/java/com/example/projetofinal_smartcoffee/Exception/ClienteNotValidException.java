package com.example.projetofinal_smartcoffee.Exception;

import com.example.projetofinal_smartcoffee.Cliente;

public class ClienteNotValidException extends RuntimeException {
    public ClienteNotValidException() {
        super("O cliente não é valido!");
    }

    public ClienteNotValidException(Cliente c) {
        super(String.format("O cliente %s (id:%i) não é valido!", c.getNome(), c.getID()));
    }
}
