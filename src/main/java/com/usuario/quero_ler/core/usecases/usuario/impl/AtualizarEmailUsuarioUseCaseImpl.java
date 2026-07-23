package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.AtualizarEmailUsuarioUseCase;
import com.usuario.quero_ler.core.exceptions.EmailJaCadastradoException;


import java.util.Optional;

public class AtualizarEmailUsuarioUseCaseImpl implements AtualizarEmailUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public AtualizarEmailUsuarioUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Usuario usuario, String novoEmail) {
        Optional<Usuario> usuarioComEmail = usuarioGateway.findByEmailIgnoreCase(novoEmail);

        if (usuarioComEmail.isPresent()
                && !usuarioComEmail.get().id().equals(usuario.id())) {
            throw new EmailJaCadastradoException("O email '" + novoEmail + "' já está cadastrado.");
        }

        return usuario.withEmail(novoEmail);
    }
}
