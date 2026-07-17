package com.usuario.quero_ler.core.exceptions;

public class LivroNaoEncontradoException extends RuntimeException{
    public LivroNaoEncontradoException(String mensagem) {
    super(mensagem);
    }
}
