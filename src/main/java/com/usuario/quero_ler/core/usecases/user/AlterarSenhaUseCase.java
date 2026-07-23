package com.usuario.quero_ler.core.usecases.user;

import com.usuario.quero_ler.core.entities.User;

public interface AlterarSenhaUseCase {
    void execute(User user, String senhaAtual, String senhaNova);
}
