package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

import java.util.Optional;

public interface LivroGateway {
    Optional<Livro> findByIsbn(String isbn);

    Optional<Livro> findById(Long id);

    Livro save(Livro livro);

    PaginatedResult<Livro> findAll(Pagination pagination);

    PaginatedResult<Livro> buscarComFiltros(String titulo, String editora, String autor, Pagination pagination);

    PaginatedResult<Livro> listarPopulares(Pagination pagination);

    byte[] buscarCapa(Long livroId);

    void inserirCapa(Long livroId, byte[] capa);
}
