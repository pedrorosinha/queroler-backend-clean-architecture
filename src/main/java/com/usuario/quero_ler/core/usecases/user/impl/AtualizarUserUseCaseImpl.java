package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.usecases.user.AtualizarUserUseCase;


public class AtualizarUserUseCaseImpl implements AtualizarUserUseCase {

    private final UserGateway userGateway;

    public AtualizarUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User execute(User user, String novoUsername) {
        user = user.withUser(novoUsername);
        return userGateway.save(user);
    }
}
