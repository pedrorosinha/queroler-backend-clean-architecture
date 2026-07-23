package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.LeituraStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class LeituraTest {

    @Test
    @DisplayName("Deve retornar novo objeto com status alterado")
    void withStatus() {
        Leitura leitura = leitura().build();

        Leitura novo = leitura.withStatus(LeituraStatus.LIVROS_QUE_QUERO_LER);

        assertEquals(LeituraStatus.LIVROS_QUE_QUERO_LER, novo.status());
        assertNull(leitura.status());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com lido alterado")
    void withLido() {
        Leitura leitura = leitura().build();

        Leitura novo = leitura.withLido(true);

        assertTrue(novo.lido());
        assertNull(leitura.lido());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com usuario alterado")
    void withUsuario() {
        Leitura leitura = leitura().build();
        Usuario usuario = usuario().id(1L).build();

        Leitura novo = leitura.withUsuario(usuario);

        assertEquals(1L, novo.usuario().id());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com livro alterado")
    void withLivro() {
        Leitura leitura = leitura().build();
        Livro livro = livro().id(1L).build();

        Leitura novo = leitura.withLivro(livro);

        assertEquals(1L, novo.livro().id());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com diarioDeLeitura alterado")
    void withDiarioDeLeitura() {
        Leitura leitura = leitura().build();
        DiarioDeLeitura diario = diarioDeLeitura().id(1L).build();

        Leitura novo = leitura.withDiarioDeLeitura(diario);

        assertEquals(1L, novo.diarioDeLeitura().id());
    }

    @Test
    @DisplayName("Deve manter imutabilidade - alterações não afetam original")
    void imutabilidade() {
        Usuario usuario = usuario().id(1L).build();
        Livro livro = livro().id(2L).build();
        Leitura original = leitura()
                .id(10L)
                .status(LeituraStatus.LIVROS_QUE_ESTOU_LENDO)
                .usuario(usuario)
                .livro(livro)
                .lido(false)
                .build();

        Leitura copia = original
                .withStatus(LeituraStatus.LIVROS_LIDOS)
                .withLido(true);

        assertEquals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO, original.status());
        assertFalse(original.lido());
        assertEquals(LeituraStatus.LIVROS_LIDOS, copia.status());
        assertTrue(copia.lido());
    }
}
