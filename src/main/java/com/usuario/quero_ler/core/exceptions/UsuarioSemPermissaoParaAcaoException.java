package com.usuario.quero_ler.core.exceptions;

public class UsuarioSemPermissaoParaAcaoException extends RuntimeException{
    public UsuarioSemPermissaoParaAcaoException(String mensagem) {
    super(mensagem);
    }
}
