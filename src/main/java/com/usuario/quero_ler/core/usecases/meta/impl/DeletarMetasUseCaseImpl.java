package com.usuario.quero_ler.core.usecases.meta.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.MetaLeituraGateway;
import com.usuario.quero_ler.core.usecases.meta.DeletarMetasUseCase;

public class DeletarMetasUseCaseImpl implements DeletarMetasUseCase {

    private final MetaLeituraGateway metaLeituraGateway;

    public DeletarMetasUseCaseImpl(MetaLeituraGateway metaLeituraGateway) {
        this.metaLeituraGateway = metaLeituraGateway;
    }

    @Override
    public void execute(Usuario usuario) {
        metaLeituraGateway.deleteAllByUsuario(usuario);
    }
}
