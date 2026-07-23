package com.usuario.quero_ler.core.usecases.user;

import com.usuario.quero_ler.core.entities.LoginResult;

public interface LoginUseCase {
    LoginResult execute(String username, String senha);
}
