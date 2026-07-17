package com.usuario.quero_ler.core.usecases.diario;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;

public interface CriarDiarioUseCase {
    void execute(DiarioDeLeitura diario, Long usuarioId, Long livroId);
}
