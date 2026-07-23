package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AcompanhamentoMapper {

    public AcompanhamentoDeLeitura toDomain(AcompanhamentoRequestDto dto) {
        if (dto == null) return null;
        return new AcompanhamentoDeLeitura(null, dto.paginaInicial(), dto.paginaFinal(), dto.comentario(), null);
    }
}
