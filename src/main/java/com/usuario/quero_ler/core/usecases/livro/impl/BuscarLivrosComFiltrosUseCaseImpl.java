package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.livro.BuscarLivrosComFiltrosUseCase;
import com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public class BuscarLivrosComFiltrosUseCaseImpl implements BuscarLivrosComFiltrosUseCase {

    private final LivroGateway livroGateway;

    public BuscarLivrosComFiltrosUseCaseImpl(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    @Override
    public PaginatedResult<Livro> execute(String titulo, String editora, String autor, Pagination pagination) {
        PaginatedResult<Livro> livros = livroGateway.buscarComFiltros(titulo, editora, autor, pagination);
        if (livros.isEmpty()) {
            throw new LivroNaoEncontradoException("Nenhum livro encontrado para essa busca!");
        }
        return livros;
    }
}
