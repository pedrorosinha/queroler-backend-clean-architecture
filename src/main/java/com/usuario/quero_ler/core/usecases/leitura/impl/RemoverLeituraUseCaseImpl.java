package com.usuario.quero_ler.core.usecases.leitura.impl;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.leitura.RemoverLeituraUseCase;
import com.usuario.quero_ler.core.exceptions.LeituraNaoEncontradaException;


public class RemoverLeituraUseCaseImpl implements RemoverLeituraUseCase {

    private final LeituraGateway leituraGateway;

    public RemoverLeituraUseCaseImpl(LeituraGateway leituraGateway) {
        this.leituraGateway = leituraGateway;
    }

    @Override
    public void execute(Long idLivro, Long idUsuario) {
        Leitura leitura = leituraGateway.findByUsuarioIdAndLivroId(idUsuario, idLivro)
                .orElseThrow(() -> new LeituraNaoEncontradaException(
                        "Leitura não encontrada para este usuário e livro."));
        leituraGateway.delete(leitura);
    }
}
