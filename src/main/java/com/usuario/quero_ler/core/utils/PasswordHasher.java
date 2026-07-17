package com.usuario.quero_ler.core.utils;

public interface PasswordHasher {
    String hash(String senha);
    boolean check(String senha, String hash);
}
