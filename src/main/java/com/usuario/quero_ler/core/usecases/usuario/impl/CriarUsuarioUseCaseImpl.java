package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.CriarUsuarioUseCase;
import com.usuario.quero_ler.core.exceptions.EmailJaCadastradoException;
import com.usuario.quero_ler.core.exceptions.CpfJaCadastradoException;
import com.usuario.quero_ler.core.utils.Cpf;

public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Usuario usuario) {
        String emailNormalizado = usuario.email().trim().toLowerCase();

        if (usuarioGateway.existsByEmailIgnoreCase(emailNormalizado)) {
            throw new EmailJaCadastradoException("O email '" + emailNormalizado + "' já está cadastrado.");
        }

        Cpf.validateOrThrow(usuario.cpf());

        String normalizedCpf = Cpf.normalize(usuario.cpf());

        if (usuarioGateway.existsByCpf(normalizedCpf)) {
            throw new CpfJaCadastradoException("CPF já cadastrado.");
        }

        usuario = usuario.withEmail(emailNormalizado);
        return usuarioGateway.save(usuario);
    }
}
