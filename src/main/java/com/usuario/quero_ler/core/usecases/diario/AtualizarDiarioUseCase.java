package com.usuario.quero_ler.core.usecases.diario;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;

public interface AtualizarDiarioUseCase {
    void execute(Long id, DiarioDeLeitura dadosAtualizados, Long usuarioId);
}
