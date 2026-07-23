package com.usuario.quero_ler.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    @Test
    @DisplayName("Deve criar lista vazia de livros quando nula")
    void livrosDefault() {
        Autor autor = autor().build();
        assertNotNull(autor.livros());
        assertTrue(autor.livros().isEmpty());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de livros")
    void livrosDefensiveCopy() {
        List<Livro> original = new ArrayList<>();
        original.add(livro().id(1L).build());

        Autor autor = autor().livros(original).build();

        original.add(livro().id(2L).build());

        assertEquals(1, autor.livros().size());
    }

    @Test
    @DisplayName("Deve criar autor com id e nome")
    void camposBasicos() {
        Autor autor = autor()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        assertEquals(1L, autor.id());
        assertEquals("Machado de Assis", autor.nome());
    }
}
