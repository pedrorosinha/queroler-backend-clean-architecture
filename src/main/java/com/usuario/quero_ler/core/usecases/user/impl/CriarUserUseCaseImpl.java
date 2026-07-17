package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.usecases.user.CriarUserUseCase;
import com.usuario.quero_ler.core.utils.PasswordHasher;
import com.usuario.quero_ler.core.utils.Senhas;

public class CriarUserUseCaseImpl implements CriarUserUseCase {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public CriarUserUseCaseImpl(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User execute(String email, String senha, UsuarioProfile profile) {
        Senhas.validar(senha);
        User user = new User(null, email, passwordHasher.hash(senha),
                profile.equals(UsuarioProfile.LEITOR), profile, null);
        return userGateway.save(user);
    }
}
