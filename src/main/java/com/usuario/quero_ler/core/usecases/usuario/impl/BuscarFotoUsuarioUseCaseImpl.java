package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.BuscarFotoUsuarioUseCase;
import com.usuario.quero_ler.core.exceptions.FotoNaoCadastradaException;
import com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException;

public class BuscarFotoUsuarioUseCaseImpl implements BuscarFotoUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public BuscarFotoUsuarioUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public byte[] execute(Long usuarioId) {
        var usuario = usuarioGateway.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (usuario.foto() == null) {
            throw new FotoNaoCadastradaException("Foto não cadastrada");
        }

        return usuario.foto();
    }
}
