package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.UsuarioProfile;

public record User(
        Long id,
        String user,
        String senha,
        Boolean senhaTrocada,
        UsuarioProfile profile,
        Usuario usuario) {

    public User() {
        this(null, null, null, null, null, null);
    }

    public User {
        senhaTrocada = senhaTrocada == null ? Boolean.FALSE : senhaTrocada;
    }

    public User withSenha(String senha) {
        return new User(id, user, senha, senhaTrocada, profile, usuario);
    }

    public User withUser(String user) {
        return new User(id, user, senha, senhaTrocada, profile, usuario);
    }

    public User withProfile(UsuarioProfile profile) {
        return new User(id, user, senha, senhaTrocada, profile, usuario);
    }

    public User withSenhaTrocada(boolean senhaTrocada) {
        return new User(id, user, senha, senhaTrocada, profile, usuario);
    }

    public User withUsuario(Usuario usuario) {
        return new User(id, user, senha, senhaTrocada, profile, usuario);
    }
}
