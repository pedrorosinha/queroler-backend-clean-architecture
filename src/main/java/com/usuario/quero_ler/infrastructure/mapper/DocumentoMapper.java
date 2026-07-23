package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoAlteracoesDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoRequestDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoResponseDto;
import com.usuario.quero_ler.infrastructure.persistence.DocumentoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DocumentoMapper {

    public Documento toDomain(DocumentoRequestDto dto) {
        return new Documento(null, dto.titulo(), dto.tipo(), dto.conteudo(), LocalDateTime.now());
    }

    public Documento toDomain(DocumentoEntity entity) {
        if (entity == null) return null;
        return new Documento(entity.getId(), entity.getTitulo(), entity.getTipo(),
                entity.getConteudo(), entity.getUltimaAlteracao());
    }

    public DocumentoEntity toPersistence(Documento domain) {
        if (domain == null) return null;
        return DocumentoEntity.builder()
                .id(domain.id())
                .titulo(domain.titulo())
                .tipo(domain.tipo())
                .conteudo(domain.conteudo())
                .ultimaAlteracao(domain.ultimaAlteracao())
                .build();
    }

    public DocumentoResponseDto toResponse(Documento domain) {
        if (domain == null) return null;
        return new DocumentoResponseDto(
                domain.id(),
                domain.titulo(),
                domain.tipo(),
                domain.conteudo(),
                domain.ultimaAlteracao()
        );
    }

    public Documento toDomain(DocumentoAlteracoesDto dto) {
        if (dto == null) return null;
        return new Documento(null, dto.titulo(), dto.tipo(), dto.conteudo(), null);
    }
}
