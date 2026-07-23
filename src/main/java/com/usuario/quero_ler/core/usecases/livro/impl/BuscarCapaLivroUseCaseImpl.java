package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.BuscarCapaLivroUseCase;


public class BuscarCapaLivroUseCaseImpl implements BuscarCapaLivroUseCase {

    private final LivroGateway livroGateway;

    public BuscarCapaLivroUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public byte[] execute(Long livroId) {
        return livroGateway.buscarCapa(livroId);
    }
}
