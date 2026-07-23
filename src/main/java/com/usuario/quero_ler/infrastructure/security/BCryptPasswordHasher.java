package com.usuario.quero_ler.infrastructure.security;

import com.usuario.quero_ler.core.utils.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String hash(String senha) {
        return delegate.encode(senha);
    }

    @Override
    public boolean check(String senha, String hash) {
        return delegate.matches(senha, hash);
    }
}
