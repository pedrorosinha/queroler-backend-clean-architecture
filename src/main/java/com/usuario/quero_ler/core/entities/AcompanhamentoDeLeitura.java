package com.usuario.quero_ler.core.entities;

public record AcompanhamentoDeLeitura(
        Long id,
        int paginaInicial,
        int paginaFinal,
        String comentario,
        DiarioDeLeitura diarioDeLeitura) {
}
