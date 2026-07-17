package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.InserirCapaLivroUseCase;

public class InserirCapaLivroUseCaseImpl implements InserirCapaLivroUseCase {

    private final LivroGateway livroGateway;

    public InserirCapaLivroUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public void execute(Long livroId, byte[] capaDoLivro) {
        livroGateway.inserirCapa(livroId, capaDoLivro);
    }
}
