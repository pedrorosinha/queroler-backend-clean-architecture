package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.usecases.usuario.AtualizarDadosAdicionaisUseCase;


public class AtualizarDadosAdicionaisUseCaseImpl implements AtualizarDadosAdicionaisUseCase {

    private final UsuarioGateway usuarioGateway;

    public AtualizarDadosAdicionaisUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Usuario usuario, String cidade, String estado, String pais, byte[] foto) {
        if (foto != null) {
            usuario = usuario.withFoto(foto);
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
        return usuarioGateway.save(usuario);
    }
}
