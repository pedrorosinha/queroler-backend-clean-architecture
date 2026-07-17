package com.usuario.quero_ler.infrastructure.dto.documento;

import com.usuario.quero_ler.core.enums.DocumentoTipo;

public record DocumentoAlteracoesDto(
        String titulo,
        DocumentoTipo tipo,
        String conteudo
) {}