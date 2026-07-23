package com.usuario.quero_ler.core.usecases.autor.impl;

import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.gateway.AutorGateway;
import com.usuario.quero_ler.core.usecases.autor.CriarAutorUseCase;


public class CriarAutorUseCaseImpl implements CriarAutorUseCase {

    private final AutorGateway autorGateway;

    public CriarAutorUseCaseImpl(AutorGateway autorGateway) {
        this.autorGateway = autorGateway;
    }

    @Override
    public Autor execute(String nome) {
        Autor autor = new Autor(null, nome, null);
        return autorGateway.save(autor);
    }
}
