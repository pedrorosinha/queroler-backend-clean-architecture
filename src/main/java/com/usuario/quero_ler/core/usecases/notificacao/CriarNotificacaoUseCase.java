package com.usuario.quero_ler.core.usecases.notificacao;

import com.usuario.quero_ler.core.entities.Notificacao;

public interface CriarNotificacaoUseCase {
    Notificacao execute(String texto);
}
