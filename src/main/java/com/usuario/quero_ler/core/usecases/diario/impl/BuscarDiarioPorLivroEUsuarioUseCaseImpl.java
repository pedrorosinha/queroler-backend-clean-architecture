package com.usuario.quero_ler.core.usecases.diario.impl;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.usecases.diario.BuscarDiarioPorLivroEUsuarioUseCase;
import com.usuario.quero_ler.core.exceptions.DiarioNaoEncontradoException;


public class BuscarDiarioPorLivroEUsuarioUseCaseImpl implements BuscarDiarioPorLivroEUsuarioUseCase {

    private final DiarioDeLeituraGateway diarioGateway;

    public BuscarDiarioPorLivroEUsuarioUseCaseImpl(DiarioDeLeituraGateway diarioGateway) {
        this.diarioGateway = diarioGateway;
    }

    @Override
    public DiarioDeLeitura execute(Long usuarioId, Long livroId) {
        return diarioGateway.findByUsuarioIdAndLivroId(usuarioId, livroId)
                .orElseThrow(() -> new DiarioNaoEncontradoException("Diario não encontrado!"));
    }
}
