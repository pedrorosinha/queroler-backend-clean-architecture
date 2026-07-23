package com.usuario.quero_ler.infrastructure.dto.leitura;

public record AcompanhamentoResponseDto(
        Long id,
        Integer paginaInicial,
        Integer paginaFinal,
        String comentario,
        Long diarioId,
        Long usuarioId) {
}
