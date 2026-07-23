package com.usuario.quero_ler.infrastructure.dto.leitura;

public record AcompanhamentoLeituraResponseDto(
		Long id,
		Integer paginaInicial,
		Integer paginaFinal,
		String comentario) {
}
