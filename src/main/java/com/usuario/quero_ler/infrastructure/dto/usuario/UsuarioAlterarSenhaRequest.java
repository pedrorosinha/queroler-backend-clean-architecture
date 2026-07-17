package com.usuario.quero_ler.infrastructure.dto.usuario;

public record UsuarioAlterarSenhaRequest(
        String senhaAtual,
        String senhaNova
) {}