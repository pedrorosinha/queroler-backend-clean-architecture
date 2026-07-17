package com.usuario.quero_ler.core.usecases.livro;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public interface ListarLivrosPopularesUseCase {
    PaginatedResult<Livro> execute(Pagination pagination);
}
