package com.usuario.quero_ler.infrastructure.dto.leitura;

import com.usuario.quero_ler.core.enums.LeituraStatus;

import jakarta.validation.constraints.NotNull;

public record AdicionarLeituraRequestDto(
        @NotNull Long livroId,
        @NotNull LeituraStatus status) {
}
