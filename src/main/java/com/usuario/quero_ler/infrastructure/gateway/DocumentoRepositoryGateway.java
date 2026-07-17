package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.core.enums.DocumentoTipo;
import com.usuario.quero_ler.core.gateway.DocumentoGateway;
import com.usuario.quero_ler.infrastructure.mapper.DocumentoMapper;
import com.usuario.quero_ler.infrastructure.persistence.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocumentoRepositoryGateway implements DocumentoGateway {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;

    @Override
    public Documento save(Documento documento) {
        var entity = documentoMapper.toPersistence(documento);
        var saved = documentoRepository.save(entity);
        return documentoMapper.toDomain(saved);
    }

    @Override
    public Optional<Documento> findById(Long id) {
        return documentoRepository.findById(id).map(documentoMapper::toDomain);
    }

    @Override
    public Documento findTopByTipoOrderByUltimaAlteracaoDesc(DocumentoTipo tipo) {
        var entity = documentoRepository.findTopByTipoOrderByUltimaAlteracaoDesc(tipo);
        return documentoMapper.toDomain(entity);
    }
}
