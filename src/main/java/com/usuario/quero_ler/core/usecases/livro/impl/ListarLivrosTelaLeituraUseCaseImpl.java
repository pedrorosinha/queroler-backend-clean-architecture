package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.livro.ListarLivrosTelaLeituraUseCase;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public class ListarLivrosTelaLeituraUseCaseImpl implements ListarLivrosTelaLeituraUseCase {

    private final LeituraGateway leituraGateway;

    public ListarLivrosTelaLeituraUseCaseImpl(LeituraGateway leituraGateway) {
        this.leituraGateway = leituraGateway;
    }

    @Override
    public PaginatedResult<Livro> execute(Long usuarioId, Pagination pagination) {
        return leituraGateway.findLivrosByUsuarioId(usuarioId, pagination);
    }
}
