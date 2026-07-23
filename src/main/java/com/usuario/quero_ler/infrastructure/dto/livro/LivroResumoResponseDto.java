package com.usuario.quero_ler.infrastructure.dto.livro;

public record LivroResumoResponseDto(
		Long id,
		String titulo,
		Integer numeroDePaginas) {
}
