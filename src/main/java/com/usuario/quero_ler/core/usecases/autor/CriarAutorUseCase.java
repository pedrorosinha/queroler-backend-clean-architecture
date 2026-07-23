package com.usuario.quero_ler.core.usecases.autor;

import com.usuario.quero_ler.core.entities.Autor;

public interface CriarAutorUseCase {
    Autor execute(String nome);
}
