package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.core.enums.DocumentoTipo;

import java.util.Optional;

public interface DocumentoGateway {
    Documento save(Documento documento);

    Optional<Documento> findById(Long id);

    Documento findTopByTipoOrderByUltimaAlteracaoDesc(DocumentoTipo tipo);
}
