package com.usuario.quero_ler.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Deve criar listas vazias quando notificacoes, livros e metasLeitura sao nulas")
    void listasDefault() {
        Usuario usuario = usuario().build();
        assertNotNull(usuario.notificacoes());
        assertTrue(usuario.notificacoes().isEmpty());
        assertNotNull(usuario.livros());
        assertTrue(usuario.livros().isEmpty());
        assertNotNull(usuario.metasLeitura());
        assertTrue(usuario.metasLeitura().isEmpty());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de notificacoes")
    void notificacoesDefensiveCopy() {
        List<UsuarioNotificacao> original = new ArrayList<>();
        original.add(usuarioNotificacao().id(1L).build());

        Usuario usuario = usuario().notificacoes(original).build();

        original.add(usuarioNotificacao().id(2L).build());

        assertEquals(1, usuario.notificacoes().size());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de livros")
    void livrosDefensiveCopy() {
        List<Leitura> original = new ArrayList<>();
        original.add(leitura().id(1L).build());

        Usuario usuario = usuario().livros(original).build();

        original.add(leitura().id(2L).build());

        assertEquals(1, usuario.livros().size());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de metasLeitura")
    void metasDefensiveCopy() {
        List<MetaLeitura> original = new ArrayList<>();
        original.add(metaLeitura().id(1L).build());

        Usuario usuario = usuario().metasLeitura(original).build();

        original.add(metaLeitura().id(2L).build());

        assertEquals(1, usuario.metasLeitura().size());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com cidade alterada")
    void withCidade() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withCidade("São Paulo");
        assertEquals("São Paulo", novo.cidade());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com estado alterado")
    void withEstado() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withEstado("SP");
        assertEquals("SP", novo.estado());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com pais alterado")
    void withPais() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withPais("Brasil");
        assertEquals("Brasil", novo.pais());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com email alterado")
    void withEmail() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withEmail("novo@test.com");
        assertEquals("novo@test.com", novo.email());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com nome alterado")
    void withNome() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withNome("João");
        assertEquals("João", novo.nome());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com dataDeNascimento alterada")
    void withDataDeNascimento() {
        Usuario usuario = usuario().build();
        var data = java.time.LocalDate.of(1990, 5, 15);
        Usuario novo = usuario.withDataDeNascimento(data);
        assertEquals(data, novo.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com aceitarTermos alterado")
    void withAceitarTermos() {
        Usuario usuario = usuario().build();
        Usuario novo = usuario.withAceitarTermos(true);
        assertTrue(novo.aceitarTermos());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com foto alterada")
    void withFoto() {
        Usuario usuario = usuario().build();
        byte[] foto = new byte[]{1, 2, 3};
        Usuario novo = usuario.withFoto(foto);
        assertArrayEquals(foto, novo.foto());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com user alterado")
    void withUser() {
        Usuario usuario = usuario().build();
        User user = user().id(1L).build();
        Usuario novo = usuario.withUser(user);
        assertEquals(1L, novo.user().id());
    }
}
