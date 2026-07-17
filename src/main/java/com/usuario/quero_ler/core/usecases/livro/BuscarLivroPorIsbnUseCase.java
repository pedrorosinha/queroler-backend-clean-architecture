package com.usuario.quero_ler.core.usecases.livro;

import com.usuario.quero_ler.core.entities.Livro;

public interface BuscarLivroPorIsbnUseCase {
    Livro execute(String isbn);
}
