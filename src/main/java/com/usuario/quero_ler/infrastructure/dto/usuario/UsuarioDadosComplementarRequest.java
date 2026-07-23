package com.usuario.quero_ler.infrastructure.dto.usuario;

public record UsuarioDadosComplementarRequest(
        String cidade,
        String estado,
        String pais
) {}