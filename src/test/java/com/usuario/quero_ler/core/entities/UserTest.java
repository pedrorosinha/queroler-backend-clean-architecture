package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.UsuarioProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Deve definir senhaTrocada como FALSE quando nulo")
    void senhaTrocadaDefault() {
        User user = user().build();
        assertFalse(user.senhaTrocada());
    }

    @Test
    @DisplayName("Deve criar User vazio com construtor sem argumentos")
    void construtorVazio() {
        User user = new User();
        assertNull(user.id());
        assertNull(user.user());
        assertNull(user.senha());
        assertFalse(user.senhaTrocada());
        assertNull(user.profile());
        assertNull(user.usuario());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com senha alterada")
    void withSenha() {
        User user = user().build();

        User novo = user.withSenha("hash123");

        assertEquals("hash123", novo.senha());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com user alterado")
    void withUser() {
        User user = user().build();

        User novo = user.withUser("email@test.com");

        assertEquals("email@test.com", novo.user());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com profile alterado")
    void withProfile() {
        User user = user().build();

        User novo = user.withProfile(UsuarioProfile.ADMINISTRADOR);

        assertEquals(UsuarioProfile.ADMINISTRADOR, novo.profile());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com senhaTrocada alterado")
    void withSenhaTrocada() {
        User user = user().build();

        User novo = user.withSenhaTrocada(true);

        assertTrue(novo.senhaTrocada());
        assertFalse(user.senhaTrocada());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com usuario alterado")
    void withUsuario() {
        User user = user().build();
        Usuario usuario = usuario().id(1L).build();

        User novo = user.withUsuario(usuario);

        assertEquals(1L, novo.usuario().id());
    }
}
