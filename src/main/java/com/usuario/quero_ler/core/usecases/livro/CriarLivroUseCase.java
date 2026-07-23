package com.usuario.quero_ler.core.usecases.livro;

import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.entities.Livro;

import java.util.List;

public interface CriarLivroUseCase {
    Livro execute(Livro livro, List<Autor> autores, byte[] capaDoLivro);
}
