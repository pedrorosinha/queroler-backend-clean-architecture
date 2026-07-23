package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.UsuarioProfile;

public record LoginResult(
        User user,
        boolean primeiroLogin) {

    public static LoginResult of(User user) {
        boolean primeiroLogin = false;
        if (user.profile() == UsuarioProfile.ADMINISTRADOR
                || user.profile() == UsuarioProfile.MODERADOR) {
            primeiroLogin = !Boolean.TRUE.equals(user.senhaTrocada());
        }
        return new LoginResult(user, primeiroLogin);
    }
}
