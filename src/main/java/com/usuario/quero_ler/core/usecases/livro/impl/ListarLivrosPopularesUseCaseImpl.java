package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.ListarLivrosPopularesUseCase;
import com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public class ListarLivrosPopularesUseCaseImpl implements ListarLivrosPopularesUseCase {

    private final LivroGateway livroGateway;

    public ListarLivrosPopularesUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public PaginatedResult<Livro> execute(Pagination pagination) {
        PaginatedResult<Livro> livros = livroGateway.listarPopulares(pagination);
        if (livros.isEmpty()) {
            throw new LivroNaoEncontradoException("Não há livros populares!");
        }
        return livros;
    }
}
