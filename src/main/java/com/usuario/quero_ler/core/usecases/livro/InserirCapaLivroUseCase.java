package com.usuario.quero_ler.core.usecases.livro;

public interface InserirCapaLivroUseCase {
    void execute(Long livroId, byte[] capaDoLivro);
}
