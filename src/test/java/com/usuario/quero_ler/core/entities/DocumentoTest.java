package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.DocumentoTipo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentoTest {

    @Test
    @DisplayName("Deve retornar novo objeto com titulo alterado")
    void withTitulo() {
        Documento doc = documento().build();
        Documento novo = doc.withTitulo("Termos de Uso");
        assertEquals("Termos de Uso", novo.titulo());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com tipo alterado")
    void withTipo() {
        Documento doc = documento().build();
        Documento novo = doc.withTipo(DocumentoTipo.TERMOS_GERAIS_DE_USO);
        assertEquals(DocumentoTipo.TERMOS_GERAIS_DE_USO, novo.tipo());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com conteudo alterado")
    void withConteudo() {
        Documento doc = documento().build();
        Documento novo = doc.withConteudo("Conteudo do documento");
        assertEquals("Conteudo do documento", novo.conteudo());
    }

    @Test
    @DisplayName("Deve retornar novo objeto com ultimaAlteracao alterada")
    void withUltimaAlteracao() {
        Documento doc = documento().build();
        LocalDateTime data = LocalDateTime.of(2026, 7, 15, 10, 0);
        Documento novo = doc.withUltimaAlteracao(data);
        assertEquals(data, novo.ultimaAlteracao());
    }

    @Test
    @DisplayName("Deve manter imutabilidade nos withXxx")
    void imutabilidade() {
        Documento original = documento()
                .id(1L)
                .titulo("Original")
                .tipo(DocumentoTipo.TERMOS_GERAIS_DE_USO)
                .build();

        Documento copia = original.withTitulo("Atualizado");

        assertEquals("Original", original.titulo());
        assertEquals("Atualizado", copia.titulo());
    }
}
