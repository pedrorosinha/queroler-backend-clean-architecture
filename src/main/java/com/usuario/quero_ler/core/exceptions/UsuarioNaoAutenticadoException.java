package com.usuario.quero_ler.core.exceptions;

public class UsuarioNaoAutenticadoException extends RuntimeException{
    public UsuarioNaoAutenticadoException(String mensagem) {
    super(mensagem);
    }
}
