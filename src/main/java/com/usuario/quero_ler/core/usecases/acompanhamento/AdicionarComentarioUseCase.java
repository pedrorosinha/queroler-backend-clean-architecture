package com.usuario.quero_ler.core.usecases.acompanhamento;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;

public interface AdicionarComentarioUseCase {
    void execute(Long diarioId, AcompanhamentoDeLeitura acompanhamento);
}
