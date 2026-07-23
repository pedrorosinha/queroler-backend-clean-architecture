package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.BuscarUsuarioUseCase;
import com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException;


public class BuscarUsuarioUseCaseImpl implements BuscarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public BuscarUsuarioUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Long id) {
        return usuarioGateway.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Não foi encontrado nenhum usuário com ID: '" + id + "'."));
    }
}
