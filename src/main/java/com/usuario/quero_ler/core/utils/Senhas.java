package com.usuario.quero_ler.core.utils;

import com.usuario.quero_ler.core.exceptions.SenhaInvalidaException;

public class Senhas {
    public static void validar(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new SenhaInvalidaException("Senha é obrigatória.");
        }

        if (senha.length() < 8) {
            throw new SenhaInvalidaException("A senha deve ter no mínimo 8 caracteres.");
        }

        if (!senha.matches(".*[A-Z].*")) {
            throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula.");
        }

        if (!senha.matches(".*[a-z].*")) {
            throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra minúscula.");
        }

        if (!senha.matches(".*\\d.*")) {
            throw new SenhaInvalidaException("A senha deve conter pelo menos um número.");
        }

        if (!senha.matches(".*[@$!%*?&.#_-].*")) {
            throw new SenhaInvalidaException("A senha deve conter pelo menos um caractere especial.");
        }
    }
}
