package com.usuario.quero_ler.core.usecases.diario;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;

public interface BuscarDiarioPorLivroEUsuarioUseCase {
    DiarioDeLeitura execute(Long usuarioId, Long livroId);
}
