package com.usuario.quero_ler.core.usecases.user;

import com.usuario.quero_ler.core.entities.User;

public interface AtualizarUserUseCase {
    User execute(User user, String novoUsername);
}
