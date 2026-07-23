package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.BuscarLivroUseCase;
import com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException;

public class BuscarLivroUseCaseImpl implements BuscarLivroUseCase {

    private final LivroGateway livroGateway;

    public BuscarLivroUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public Livro execute(Long id) {
        return livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não cadastrado."));
    }
}
