package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.BuscarLivroPorIsbnUseCase;
import com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException;


public class BuscarLivroPorIsbnUseCaseImpl implements BuscarLivroPorIsbnUseCase {

    private final LivroGateway livroGateway;

    public BuscarLivroPorIsbnUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public Livro execute(String isbn) {
        return livroGateway.findByIsbn(isbn)
                .orElseThrow(() -> new LivroNaoEncontradoException(
                        "Não há nenhum livro cadastrado com o código ISBN informado."));
    }
}
