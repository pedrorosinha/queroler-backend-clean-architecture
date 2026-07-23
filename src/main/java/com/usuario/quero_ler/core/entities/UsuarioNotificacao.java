package com.usuario.quero_ler.core.entities;

import java.time.LocalDateTime;

public record UsuarioNotificacao(
        Long id,
        Usuario usuario,
        Notificacao notificacao,
        Boolean visualizada,
        LocalDateTime dataLeitura) {
}
