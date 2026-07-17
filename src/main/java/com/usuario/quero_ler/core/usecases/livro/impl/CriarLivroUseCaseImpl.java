package com.usuario.quero_ler.core.usecases.livro.impl;

import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.usecases.autor.CriarAutorUseCase;
import com.usuario.quero_ler.core.usecases.livro.CriarLivroUseCase;
import com.usuario.quero_ler.core.exceptions.IsbnJaCadastradoException;

import java.util.ArrayList;
import java.util.List;

public class CriarLivroUseCaseImpl implements CriarLivroUseCase {

    private final LivroGateway livroGateway;
    private final CriarAutorUseCase criarAutorUseCase;

    public CriarLivroUseCaseImpl(LivroGateway livroGateway, CriarAutorUseCase criarAutorUseCase) {
        this.livroGateway = livroGateway;
        this.criarAutorUseCase = criarAutorUseCase;
    }

    @Override
    public Livro execute(Livro livro, List<Autor> autores, byte[] capaDoLivro) {
        if (livroGateway.findByIsbn(livro.isbn()).isPresent()) {
            throw new IsbnJaCadastradoException("ISBN já cadastrado");
        }

        List<Autor> autoresSalvos = new ArrayList<>();
        for (Autor autor : autores) {
            Autor salvo = criarAutorUseCase.execute(autor.nome());
            autoresSalvos.add(salvo);
        }
        livro = livro.withAutores(autoresSalvos);

        if (capaDoLivro != null && capaDoLivro.length > 0) {
            livro = livro.withCapaDoLivro(capaDoLivro);
        }

        return livroGateway.save(livro);
    }
}
