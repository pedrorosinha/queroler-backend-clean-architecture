package com.usuario.quero_ler.core.entities;

import java.time.LocalDateTime;

public record Notificacao(
        Long id,
        LocalDateTime dataDeCriacao,
        String notificacao,
        Boolean lido) {
    public Notificacao {
        lido = lido == null ? Boolean.FALSE : lido;
    }
}
