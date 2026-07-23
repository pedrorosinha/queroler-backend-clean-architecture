package com.usuario.quero_ler.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class MetaLeituraTest {

    @Test
    @DisplayName("Deve criar lista vazia de livrosMeta quando nula")
    void livrosMetaDefault() {
        MetaLeitura meta = metaLeitura().build();
        assertNotNull(meta.livrosMeta());
        assertTrue(meta.livrosMeta().isEmpty());
    }

    @Test
    @DisplayName("Deve criar copia defensiva da lista de livrosMeta")
    void livrosMetaDefensiveCopy() {
        List<LivroMeta> original = new ArrayList<>();
        original.add(livroMeta().id(1L).build());

        MetaLeitura meta = metaLeitura().livrosMeta(original).build();

        original.add(livroMeta().id(2L).build());

        assertEquals(1, meta.livrosMeta().size());
    }

    @Test
    @DisplayName("Deve retornar true quando pertence ao ano atual")
    void pertenceAoAnoAtual() {
        MetaLeitura meta = metaLeitura()
                .ano(LocalDate.now().getYear())
                .build();

        assertTrue(meta.pertenceAoAnoAtual());
    }

    @Test
    @DisplayName("Deve retornar false quando não pertence ao ano atual")
    void naoPertenceAoAnoAtual() {
        MetaLeitura meta = metaLeitura()
                .ano(2020)
                .build();

        assertFalse(meta.pertenceAoAnoAtual());
    }

    @Test
    @DisplayName("Deve retornar true quando possui metaLivrosAno")
    void possuiMetaLivrosAno() {
        MetaLeitura meta = metaLeitura()
                .metaLivrosAno(12)
                .build();

        assertTrue(meta.possuiAlgumaMeta());
    }

    @Test
    @DisplayName("Deve retornar true quando possui metaLivrosMes")
    void possuiMetaLivrosMes() {
        MetaLeitura meta = metaLeitura()
                .metaLivrosMes(2)
                .build();

        assertTrue(meta.possuiAlgumaMeta());
    }

    @Test
    @DisplayName("Deve retornar true quando possui metaPaginasDia")
    void possuiMetaPaginasDia() {
        MetaLeitura meta = metaLeitura()
                .metaPaginasDia(30)
                .build();

        assertTrue(meta.possuiAlgumaMeta());
    }

    @Test
    @DisplayName("Deve retornar true quando possui livrosMeta nao vazio")
    void possuiLivrosMeta() {
        MetaLeitura meta = metaLeitura()
                .livrosMeta(List.of(livroMeta().id(1L).build()))
                .build();

        assertTrue(meta.possuiAlgumaMeta());
    }

    @Test
    @DisplayName("Deve retornar false quando nenhuma meta definida")
    void naoPossuiNenhumaMeta() {
        MetaLeitura meta = metaLeitura().build();

        assertFalse(meta.possuiAlgumaMeta());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com usuario alterado")
    void withUsuario() {
        MetaLeitura meta = metaLeitura().build();
        Usuario usuario = usuario().id(1L).build();

        MetaLeitura novo = meta.withUsuario(usuario);

        assertEquals(1L, novo.usuario().id());
    }
}
