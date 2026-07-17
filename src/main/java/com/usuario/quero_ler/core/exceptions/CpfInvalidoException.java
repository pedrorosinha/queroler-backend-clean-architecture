package com.usuario.quero_ler.core.exceptions;

public class CpfInvalidoException extends RuntimeException {
    public CpfInvalidoException() {
        super("CPF inválido.");
    }

    public CpfInvalidoException(String message) {
        super(message);
    }
}
