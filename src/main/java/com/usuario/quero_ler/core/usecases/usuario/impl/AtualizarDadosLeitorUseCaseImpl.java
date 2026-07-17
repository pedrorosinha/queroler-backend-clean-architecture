package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.AtualizarDadosLeitorUseCase;
import com.usuario.quero_ler.core.exceptions.EmailJaCadastradoException;


import java.time.LocalDate;
import java.util.Optional;

public class AtualizarDadosLeitorUseCaseImpl implements AtualizarDadosLeitorUseCase {

    private final UsuarioGateway usuarioGateway;
    private final UserGateway userGateway;

    public AtualizarDadosLeitorUseCaseImpl(UsuarioGateway usuarioGateway, UserGateway userGateway) {
        this.usuarioGateway = usuarioGateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(Usuario usuario, String novoEmail, String nome, LocalDate dataDeNascimento,
                        String cidade, String estado, String pais, byte[] foto) {
        String emailNormalizado = novoEmail.trim().toLowerCase();

        Optional<Usuario> usuarioComEmail = usuarioGateway.findByEmailIgnoreCase(emailNormalizado);

        if (usuarioComEmail.isPresent()
                && !usuarioComEmail.get().id().equals(usuario.id())) {
            throw new EmailJaCadastradoException("O email '" + emailNormalizado + "' já está cadastrado.");
        }

        String emailAntigo = usuario.email();

        if (foto != null) {
            usuario = usuario.withFoto(foto);
        }
        if (nome != null) {
            usuario = usuario.withNome(nome);
        }
        if (dataDeNascimento != null) {
            usuario = usuario.withDataDeNascimento(dataDeNascimento);
        }
        if (cidade != null) {
            usuario = usuario.withCidade(cidade);
        }
        if (estado != null) {
            usuario = usuario.withEstado(estado);
        }
        if (pais != null) {
            usuario = usuario.withPais(pais);
        }

        usuario = usuario.withEmail(emailNormalizado);
        usuarioGateway.save(usuario);

        User user = userGateway.findByUserIgnoreCase(emailAntigo)
                .orElseThrow(() -> new com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException("Usuário não encontrado para o email: " + emailAntigo));
        user = user.withUser(emailNormalizado);
        userGateway.save(user);
    }
}
