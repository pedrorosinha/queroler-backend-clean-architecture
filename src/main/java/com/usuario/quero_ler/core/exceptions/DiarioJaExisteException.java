package com.usuario.quero_ler.core.exceptions;

public class DiarioJaExisteException extends RuntimeException {
    public DiarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}
