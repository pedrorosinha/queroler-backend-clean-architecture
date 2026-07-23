package com.usuario.quero_ler.core.usecases.acompanhamento.impl;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.core.gateway.AcompanhamentoDeLeituraGateway;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorUsuarioUseCase;


import java.util.List;

public class ListarAcompanhamentoPorUsuarioUseCaseImpl implements ListarAcompanhamentoPorUsuarioUseCase {

    private final AcompanhamentoDeLeituraGateway acompanhamentoGateway;

    public ListarAcompanhamentoPorUsuarioUseCaseImpl(AcompanhamentoDeLeituraGateway acompanhamentoGateway) {
        this.acompanhamentoGateway = acompanhamentoGateway;
    }

    @Override
    public List<AcompanhamentoDeLeitura> execute(Long usuarioId) {
        return acompanhamentoGateway.findByUsuarioIdWithJoins(usuarioId);
    }
}
