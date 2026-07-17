package com.usuario.quero_ler.core.usecases.user;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;

public interface CriarUserUseCase {
    User execute(String email, String senha, UsuarioProfile profile);
}
