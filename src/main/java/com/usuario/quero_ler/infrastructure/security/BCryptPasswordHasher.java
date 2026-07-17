package com.usuario.quero_ler.infrastructure.security;

import com.usuario.quero_ler.core.utils.PasswordHasher;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    @Override
    public boolean check(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }
}
