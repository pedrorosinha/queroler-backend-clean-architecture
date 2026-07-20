package com.usuario.quero_ler.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    @Test
    @DisplayName("Deve definir quantidadeDeUso como 0 quando nula")
    void quantidadeDeUsoDefault() {
        Livro livro = livro().build();
        assertEquals(0, livro.quantidadeDeUso());
    }

    @Test
    @DisplayName("Deve definir dataDeCadastro como now quando nula")
    void dataDeCadastroDefault() {
        Livro livro = livro().build();
        assertNotNull(livro.dataDeCadastro());
        assertTrue(livro.dataDeCadastro().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve criar listas vazias quando autores e usuarios sao nulos")
    void listasDefault() {
        Livro livro = livro().build();
        assertNotNull(livro.autores());
        assertTrue(livro.autores().isEmpty());
        assertNotNull(livro.usuarios());
        assertTrue(livro.usuarios().isEmpty());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de autores")
    void autoresDefensiveCopy() {
        List<Autor> original = new ArrayList<>();
        original.add(autor().id(1L).build());

        Livro livro = livro().autores(original).build();

        original.add(autor().id(2L).build());

        assertEquals(1, livro.autores().size());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de usuarios")
    void usuariosDefensiveCopy() {
        List<Leitura> original = new ArrayList<>();
        original.add(leitura().id(1L).build());

        Livro livro = livro().usuarios(original).build();

        original.add(leitura().id(2L).build());

        assertEquals(1, livro.usuarios().size());
    }

    @Test
    @DisplayName("Deve incrementar quantidadeDeUso em 1")
    void incrementarUso() {
        Livro livro = livro().quantidadeDeUso(5).build();

        Livro incrementado = livro.incrementarUso();

        assertEquals(6, incrementado.quantidadeDeUso());
        assertEquals(5, livro.quantidadeDeUso());
    }

    @Test
    @DisplayName("Deve incrementar de 0 para 1")
    void incrementarUsoDeZero() {
        Livro livro = livro().build();

        Livro incrementado = livro.incrementarUso();

        assertEquals(1, incrementado.quantidadeDeUso());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com capa alterada")
    void withCapaDoLivro() {
        Livro livro = livro().build();
        byte[] capa = new byte[]{1, 2, 3};

        Livro novo = livro.withCapaDoLivro(capa);

        assertArrayEquals(capa, novo.capaDoLivro());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com autores alterados")
    void withAutores() {
        Livro livro = livro().build();
        List<Autor> autores = List.of(autor().id(1L).build());

        Livro novo = livro.withAutores(autores);

        assertEquals(1, novo.autores().size());
    }
}
