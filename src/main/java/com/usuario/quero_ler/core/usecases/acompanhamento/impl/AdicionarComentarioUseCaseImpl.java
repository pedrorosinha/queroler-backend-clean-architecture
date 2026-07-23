package com.usuario.quero_ler.core.usecases.acompanhamento.impl;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.gateway.AcompanhamentoDeLeituraGateway;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.usecases.acompanhamento.AdicionarComentarioUseCase;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;

import java.util.ArrayList;
import java.util.List;

public class AdicionarComentarioUseCaseImpl implements AdicionarComentarioUseCase {

    private final AcompanhamentoDeLeituraGateway acompanhamentoGateway;
    private final DiarioDeLeituraGateway diarioGateway;

    public AdicionarComentarioUseCaseImpl(AcompanhamentoDeLeituraGateway acompanhamentoGateway,
            DiarioDeLeituraGateway diarioGateway) {
        this.acompanhamentoGateway = acompanhamentoGateway;
        this.diarioGateway = diarioGateway;
    }

    @Override
    public void execute(Long diarioId, AcompanhamentoDeLeitura acompanhamento) {
        if (acompanhamento == null) {
            throw new DadosDiarioInvalidoException("Payload do acompanhamento está vazio.");
        }

        DiarioDeLeitura diario = diarioGateway.findById(diarioId)
                .orElseThrow(() -> new DadosDiarioInvalidoException("Diário de leitura não encontrado."));

        AcompanhamentoDeLeitura novo = new AcompanhamentoDeLeitura(null,
                acompanhamento.paginaInicial(), acompanhamento.paginaFinal(),
                acompanhamento.comentario(), diario);

        acompanhamentoGateway.save(novo);
        List<AcompanhamentoDeLeitura> comentarios = new ArrayList<>(diario.comentarios());
        comentarios.add(novo);
        diario = diario.withComentarios(comentarios);
        diarioGateway.save(diario);
    }
}
