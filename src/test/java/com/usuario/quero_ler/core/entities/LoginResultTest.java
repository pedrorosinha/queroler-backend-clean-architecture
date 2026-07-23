package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.UsuarioProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginResultTest {

    @Test
    @DisplayName("Deve retornar primeiroLogin true para ADMINISTRADOR com senhaTrocada false")
    void adminPrimeiroLogin() {
        User user = user()
                .profile(UsuarioProfile.ADMINISTRADOR)
                .senhaTrocada(false)
                .build();

        LoginResult result = LoginResult.of(user);

        assertTrue(result.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin true para MODERADOR com senhaTrocada false")
    void moderadorPrimeiroLogin() {
        User user = user()
                .profile(UsuarioProfile.MODERADOR)
                .senhaTrocada(false)
                .build();

        LoginResult result = LoginResult.of(user);

        assertTrue(result.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin false para ADMINISTRADOR com senhaTrocada true")
    void adminSenhaTrocada() {
        User user = user()
                .profile(UsuarioProfile.ADMINISTRADOR)
                .senhaTrocada(true)
                .build();

        LoginResult result = LoginResult.of(user);

        assertFalse(result.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin false para MODERADOR com senhaTrocada true")
    void moderadorSenhaTrocada() {
        User user = user()
                .profile(UsuarioProfile.MODERADOR)
                .senhaTrocada(true)
                .build();

        LoginResult result = LoginResult.of(user);

        assertFalse(result.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin false para LEITOR sempre")
    void leitorNuncaPrimeiroLogin() {
        User user = user()
                .profile(UsuarioProfile.LEITOR)
                .senhaTrocada(false)
                .build();

        LoginResult result = LoginResult.of(user);

        assertFalse(result.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin false para LEITOR com senhaTrocada true")
    void leitorSenhaTrocada() {
        User user = user()
                .profile(UsuarioProfile.LEITOR)
                .senhaTrocada(true)
                .build();

        LoginResult result = LoginResult.of(user);

        assertFalse(result.primeiroLogin());
    }
}
