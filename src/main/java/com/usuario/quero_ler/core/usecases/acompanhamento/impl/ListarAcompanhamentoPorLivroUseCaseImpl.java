package com.usuario.quero_ler.core.usecases.acompanhamento.impl;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.core.gateway.AcompanhamentoDeLeituraGateway;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorLivroUseCase;


import java.util.List;

public class ListarAcompanhamentoPorLivroUseCaseImpl implements ListarAcompanhamentoPorLivroUseCase {

    private final AcompanhamentoDeLeituraGateway acompanhamentoGateway;

    public ListarAcompanhamentoPorLivroUseCaseImpl(AcompanhamentoDeLeituraGateway acompanhamentoGateway) {
        this.acompanhamentoGateway = acompanhamentoGateway;
    }

    @Override
    public List<AcompanhamentoDeLeitura> execute(Long livroId) {
        return acompanhamentoGateway.findByLivroIdWithJoins(livroId);
    }
}
