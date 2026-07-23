package com.usuario.quero_ler.core.usecases.leitura;

import com.usuario.quero_ler.core.enums.LeituraStatus;

public interface AdicionarLeituraUseCase {
    void execute(Long idLivro, Long idUsuario, LeituraStatus status);
}
