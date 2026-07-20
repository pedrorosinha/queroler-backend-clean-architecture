package com.usuario.quero_ler.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import com.usuario.quero_ler.core.enums.DocumentoTipo;

public interface DocumentoRepository extends JpaRepository<DocumentoEntity, Long> {
    DocumentoEntity findTopByTipoOrderByUltimaAlteracaoDesc(DocumentoTipo tipo);
}