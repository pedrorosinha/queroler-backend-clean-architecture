package com.usuario.quero_ler.infrastructure.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import com.usuario.quero_ler.infrastructure.dto.autor.AutorResponse;

public record LivroCardResponse (
        String urlCapaDoLivro,
        String titulo,
        String editora,
        Year anoDePublicacao,
        Integer numeroDePaginas,
        @Schema(description = "Data de cadastro no formato dd/MM/yyyy HH:mm:ss", example = "08/03/2026 11:30:00")
        LocalDateTime dataDeCadastro,
        List<AutorResponse> autores
){}