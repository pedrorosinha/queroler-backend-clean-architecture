package com.usuario.quero_ler.core.exceptions;

public class EmailNaoCadastradoException extends RuntimeException{
    public EmailNaoCadastradoException(String mensagem) {
    super(mensagem);
    }
}
