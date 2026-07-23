package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.exceptions.CredenciaisInvalidasException;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.usecases.user.AlterarSenhaUseCase;
import com.usuario.quero_ler.core.utils.PasswordHasher;
import com.usuario.quero_ler.core.utils.Senhas;

public class AlterarSenhaUseCaseImpl implements AlterarSenhaUseCase {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public AlterarSenhaUseCaseImpl(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void execute(User user, String senhaAtual, String senhaNova) {
        Senhas.validar(senhaNova);

        if (!passwordHasher.check(senhaAtual, user.senha())) {
            throw new CredenciaisInvalidasException("A senha digitada não corresponde a atual.");
        }

        String novaSenha = passwordHasher.hash(senhaNova);

        if (user.profile().equals(UsuarioProfile.ADMINISTRADOR)
                || user.profile().equals(UsuarioProfile.MODERADOR)) {
            if (Boolean.FALSE.equals(user.senhaTrocada())) {
                user = user.withSenhaTrocada(true);
            }
        }

        user = user.withSenha(novaSenha);
        userGateway.save(user);
    }
}
