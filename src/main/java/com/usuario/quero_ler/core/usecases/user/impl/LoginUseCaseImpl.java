package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.LoginResult;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.exceptions.CredenciaisInvalidasException;
import com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.usecases.user.LoginUseCase;
import com.usuario.quero_ler.core.utils.PasswordHasher;

public class LoginUseCaseImpl implements LoginUseCase {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public LoginUseCaseImpl(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoginResult execute(String username, String senha) {
        User user = userGateway.findByUserIgnoreCase(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não cadastrado"));

        if (!passwordHasher.check(senha, user.senha())) {
            throw new CredenciaisInvalidasException("Senha incorreta.");
        }

        return LoginResult.of(user);
    }
}
