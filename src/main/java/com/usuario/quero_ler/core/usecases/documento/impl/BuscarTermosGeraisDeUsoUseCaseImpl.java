package com.usuario.quero_ler.core.usecases.documento.impl;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.core.enums.DocumentoTipo;
import com.usuario.quero_ler.core.exceptions.DocumentoNaoEncontradoException;
import com.usuario.quero_ler.core.gateway.DocumentoGateway;
import com.usuario.quero_ler.core.usecases.documento.BuscarTermosGeraisDeUsoUseCase;


public class BuscarTermosGeraisDeUsoUseCaseImpl implements BuscarTermosGeraisDeUsoUseCase {

    private final DocumentoGateway documentoGateway;

    public BuscarTermosGeraisDeUsoUseCaseImpl(DocumentoGateway documentoGateway) {
        this.documentoGateway = documentoGateway;
    }

    @Override
    public Documento execute() {
        Documento doc = documentoGateway.findTopByTipoOrderByUltimaAlteracaoDesc(DocumentoTipo.TERMOS_GERAIS_DE_USO);
        if (doc == null) {
            throw new DocumentoNaoEncontradoException("Termos Gerais de Uso não encontrados.");
        }
        return doc;
    }
}
