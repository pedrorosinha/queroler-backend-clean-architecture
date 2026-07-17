package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.LeituraStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class DiarioDeLeituraTest {

    @Test
    @DisplayName("Deve definir spoiler como FALSE quando nulo")
    void spoilerDefaultFalse() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        assertFalse(diario.spoiler());
    }

    @Test
    @DisplayName("Deve definir nota como 0.0 quando nula")
    void notaDefaultZero() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        assertEquals(0.0, diario.nota());
    }

    @Test
    @DisplayName("Deve criar lista vazia de comentarios quando nula")
    void comentariosDefaultEmpty() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        assertNotNull(diario.comentarios());
        assertTrue(diario.comentarios().isEmpty());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de comentarios")
    void comentariosDefensiveCopy() {
        List<AcompanhamentoDeLeitura> original = new ArrayList<>();
        original.add(acompanhamentoDeLeitura().id(1L).build());

        DiarioDeLeitura diario = diarioDeLeitura()
                .comentarios(original)
                .build();

        original.add(acompanhamentoDeLeitura().id(2L).build());

        assertEquals(1, diario.comentarios().size());
    }

    @Test
    @DisplayName("Deve preservar valores quando spoiler é definido")
    void spoilerExplicito() {
        DiarioDeLeitura diario = diarioDeLeitura()
                .spoiler(true)
                .nota(4.5)
                .build();

        assertTrue(diario.spoiler());
        assertEquals(4.5, diario.nota());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com leitura alterada via withLeitura")
    void withLeitura() {
        Leitura leitura = leitura().id(1L).build();
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withLeitura(leitura);

        assertEquals(1L, novo.leitura().id());
        assertNull(diario.leitura());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com inicioDaLeitura alterado")
    void withInicioDaLeitura() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        var data = java.time.LocalDateTime.of(2026, 1, 15, 10, 0);

        DiarioDeLeitura novo = diario.withInicioDaLeitura(data);

        assertEquals(data, novo.inicioDaLeitura());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com terminoDaLeitura alterado")
    void withTerminoDaLeitura() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        var data = java.time.LocalDateTime.of(2026, 2, 15, 10, 0);

        DiarioDeLeitura novo = diario.withTerminoDaLeitura(data);

        assertEquals(data, novo.terminoDaLeitura());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com paginasLidas alterado")
    void withPaginasLidas() {
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withPaginasLidas(50);

        assertEquals(50, novo.paginasLidas());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com nota alterada")
    void withNota() {
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withNota(4.5);

        assertEquals(4.5, novo.nota());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com tituloDaResenha alterado")
    void withTituloDaResenha() {
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withTituloDaResenha("Meu titulo");

        assertEquals("Meu titulo", novo.tituloDaResenha());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com spoiler alterado")
    void withSpoiler() {
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withSpoiler(true);

        assertTrue(novo.spoiler());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com resenha alterada")
    void withResenha() {
        DiarioDeLeitura diario = diarioDeLeitura().build();

        DiarioDeLeitura novo = diario.withResenha("Minha resenha");

        assertEquals("Minha resenha", novo.resenha());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com comentarios alterados")
    void withComentarios() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        List<AcompanhamentoDeLeitura> lista = List.of(
                acompanhamentoDeLeitura().id(1L).build());

        DiarioDeLeitura novo = diario.withComentarios(lista);

        assertEquals(1, novo.comentarios().size());
    }
}
