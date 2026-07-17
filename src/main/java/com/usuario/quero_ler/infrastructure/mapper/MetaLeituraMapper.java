package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.infrastructure.dto.meta.MetaRequestDto;
import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.infrastructure.persistence.MetaLeituraEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MetaLeituraMapper {

    public MetaLeitura toDomain(MetaRequestDto dto) {
        Integer ano = LocalDate.now().getYear();
        return new MetaLeitura(null, dto.ano() != null ? dto.ano() : ano,
                dto.metaLivrosAno() != null ? dto.metaLivrosAno() : 0,
                dto.metaLivrosMes() != null ? dto.metaLivrosMes() : 0,
                dto.metaPaginasDia() != null ? dto.metaPaginasDia() : 0,
                null, null);
    }

    public MetaLeitura toDomain(MetaLeituraEntity entity) {
        if (entity == null) return null;
        return new MetaLeitura(entity.getId(), entity.getAno(),
                entity.getMetaLivrosAno(), entity.getMetaLivrosMes(),
                entity.getMetaPaginasDia(), null, null);
    }

    public MetaLeituraEntity toPersistence(MetaLeitura domain) {
        if (domain == null) return null;
        return MetaLeituraEntity.builder()
                .id(domain.id())
                .ano(domain.ano())
                .metaLivrosAno(domain.metaLivrosAno())
                .metaLivrosMes(domain.metaLivrosMes())
                .metaPaginasDia(domain.metaPaginasDia())
                .build();
    }
}
