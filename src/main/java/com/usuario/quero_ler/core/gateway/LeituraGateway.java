package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

import java.util.Optional;

public interface LeituraGateway {
    Optional<Leitura> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId);

    Leitura save(Leitura leitura);

    void delete(Leitura leitura);

    PaginatedResult<Leitura> findAllByUsuarioId(Long usuarioId, Pagination pagination);

    PaginatedResult<Livro> findLivrosByUsuarioId(Long usuarioId, Pagination pagination);
}
