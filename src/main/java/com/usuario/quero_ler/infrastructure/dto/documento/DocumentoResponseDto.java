package com.usuario.quero_ler.infrastructure.dto.documento;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import com.usuario.quero_ler.core.enums.DocumentoTipo;

@Schema(description = "DTO de resposta de um documento")
public record DocumentoResponseDto(
        @Schema(description = "Identificador do documento", example = "1")
        Long id,

        @Schema(description = "Título do documento", example = "Política de Segurança da Informação")
        String titulo,

        @Schema(description = "Tipo do documento")
        DocumentoTipo tipo,

        @Schema(description = "Conteúdo do documento",example = "Este documento descreve as políticas de segurança...")
        String conteudo,

        @Schema(description = "Data e hora da última alteração do documento", example = "08/03/2026 11:30:00")
        LocalDateTime ultimaAlteracao
) {
}