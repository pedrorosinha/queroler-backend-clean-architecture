package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.AtualizarDadosAdministradorUseCase;


import java.time.LocalDate;

public class AtualizarDadosAdministradorUseCaseImpl implements AtualizarDadosAdministradorUseCase {

    private final UsuarioGateway usuarioGateway;

    public AtualizarDadosAdministradorUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void execute(Usuario usuario, LocalDate dataDeNascimento,
                        String cidade, String estado, String pais, byte[] foto) {
        if (foto != null) {
            usuario = usuario.withFoto(foto);
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
        usuarioGateway.save(usuario);
    }
}
