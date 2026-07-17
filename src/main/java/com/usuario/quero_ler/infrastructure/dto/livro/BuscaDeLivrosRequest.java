package com.usuario.quero_ler.infrastructure.dto.livro;

import com.usuario.quero_ler.core.enums.TiposDeBusca;

public record BuscaDeLivrosRequest(
        TiposDeBusca tiposDeBusca,
        String valor
) {}