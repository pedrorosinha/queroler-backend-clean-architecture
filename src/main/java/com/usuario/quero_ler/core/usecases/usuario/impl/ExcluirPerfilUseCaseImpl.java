package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.usecases.usuario.ExcluirPerfilUseCase;
import com.usuario.quero_ler.core.exceptions.UsuarioSemPermissaoParaAcaoException;


import java.util.List;

public class ExcluirPerfilUseCaseImpl implements ExcluirPerfilUseCase {

    private final UsuarioGateway usuarioGateway;
    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;

    public ExcluirPerfilUseCaseImpl(UsuarioGateway usuarioGateway,
                                    UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        this.usuarioGateway = usuarioGateway;
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
    }

    @Override
    public void execute(Usuario usuario, UsuarioProfile profile) {
        if (!profile.equals(UsuarioProfile.LEITOR)) {
            throw new UsuarioSemPermissaoParaAcaoException("Ação não permitida para este usuário.");
        }

        List<UsuarioNotificacao> notificacoes = usuarioNotificacaoGateway.findByUsuarioId(usuario.id());
        for (UsuarioNotificacao un : notificacoes) {
            usuarioNotificacaoGateway.delete(un);
        }

        usuarioGateway.delete(usuario);
    }
}
