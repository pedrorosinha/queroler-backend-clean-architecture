package com.usuario.quero_ler.core.usecases.leitura.impl;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.exceptions.LeituraEstadoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ControleStatusLeituraUseCase")
class ControleStatusLeituraUseCaseImplTest {

    @InjectMocks
    private ControleStatusLeituraUseCaseImpl useCase;

    private Leitura novaLeitura(LeituraStatus status) {
        return leitura().status(status).build();
    }

    // --- FROM NULL ---

    @Test
    @DisplayName("Deve transicionar de NULL para QUERO_LER")
    void deNullParaQueroLer() {
        Leitura leitura = novaLeitura(null);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_QUE_QUERO_LER);
        assertEquals(LeituraStatus.LIVROS_QUE_QUERO_LER, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de NULL para LENDO")
    void deNullParaLendo() {
        Leitura leitura = novaLeitura(null);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        assertEquals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de NULL para LIDOS com lido=true")
    void deNullParaLidos() {
        Leitura leitura = novaLeitura(null);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_LIDOS);
        assertEquals(LeituraStatus.LIVROS_LIDOS, resultado.status());
        assertTrue(resultado.lido());
    }

    @Test
    @DisplayName("Deve lancar excecao de NULL para RELENDO")
    void deNullParaRelendo() {
        Leitura leitura = novaLeitura(null);
        assertThrows(LeituraEstadoInvalidoException.class,
                () -> useCase.execute(leitura, LeituraStatus.RELENDO));
    }

    // --- FROM QUERO_LER ---

    @Test
    @DisplayName("Deve transicionar de QUERO_LER para LENDO")
    void deQueroLerParaLendo() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_QUERO_LER);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        assertEquals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de QUERO_LER para ABANDONADOS")
    void deQueroLerParaAbandonados() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_QUERO_LER);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_ABANDONADOS);
        assertEquals(LeituraStatus.LIVROS_ABANDONADOS, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de QUERO_LER para LIDOS com lido=true")
    void deQueroLerParaLidos() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_QUERO_LER);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_LIDOS);
        assertEquals(LeituraStatus.LIVROS_LIDOS, resultado.status());
        assertTrue(resultado.lido());
    }

    @Test
    @DisplayName("Deve lancar excecao de QUERO_LER para RELENDO")
    void deQueroLerParaRelendo() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_QUERO_LER);
        assertThrows(LeituraEstadoInvalidoException.class,
                () -> useCase.execute(leitura, LeituraStatus.RELENDO));
    }

    // --- FROM LENDO ---

    @Test
    @DisplayName("Deve transicionar de LENDO para ABANDONADOS")
    void deLendoParaAbandonados() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_ABANDONADOS);
        assertEquals(LeituraStatus.LIVROS_ABANDONADOS, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de LENDO para LIDOS com lido=true")
    void deLendoParaLidos() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_LIDOS);
        assertEquals(LeituraStatus.LIVROS_LIDOS, resultado.status());
        assertTrue(resultado.lido());
    }

    @Test
    @DisplayName("Deve lancar excecao de LENDO para QUERO_LER")
    void deLendoParaQueroLer() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        assertThrows(LeituraEstadoInvalidoException.class,
                () -> useCase.execute(leitura, LeituraStatus.LIVROS_QUE_QUERO_LER));
    }

    // --- FROM RELENDO ---

    @Test
    @DisplayName("Deve transicionar de RELENDO para ABANDONADOS")
    void deRelendoParaAbandonados() {
        Leitura leitura = novaLeitura(LeituraStatus.RELENDO);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_ABANDONADOS);
        assertEquals(LeituraStatus.LIVROS_ABANDONADOS, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de RELENDO para LIDOS com lido=true")
    void deRelendoParaLidos() {
        Leitura leitura = novaLeitura(LeituraStatus.RELENDO);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_LIDOS);
        assertEquals(LeituraStatus.LIVROS_LIDOS, resultado.status());
        assertTrue(resultado.lido());
    }

    // --- FROM ABANDONADOS ---

    @Test
    @DisplayName("Deve transicionar de ABANDONADOS para LENDO")
    void deAbandonadosParaLendo() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_ABANDONADOS);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        assertEquals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO, resultado.status());
    }

    @Test
    @DisplayName("Deve transicionar de ABANDONADOS para QUERO_LER quando lido=null")
    void deAbandonadosParaQueroLer() {
        Leitura leitura = leitura().status(LeituraStatus.LIVROS_ABANDONADOS).lido(null).build();
        Leitura resultado = useCase.execute(leitura, LeituraStatus.LIVROS_QUE_QUERO_LER);
        assertEquals(LeituraStatus.LIVROS_QUE_QUERO_LER, resultado.status());
    }

    @Test
    @DisplayName("Deve lancar excecao de ABANDONADOS para QUERO_LER quando lido=true")
    void deAbandonadosParaQueroLerComLido() {
        Leitura leitura = leitura().status(LeituraStatus.LIVROS_ABANDONADOS).lido(true).build();
        assertThrows(LeituraEstadoInvalidoException.class,
                () -> useCase.execute(leitura, LeituraStatus.LIVROS_QUE_QUERO_LER));
    }

    // --- FROM LIDOS ---

    @Test
    @DisplayName("Deve transicionar de LIDOS para RELENDO")
    void deLidosParaRelendo() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_LIDOS);
        Leitura resultado = useCase.execute(leitura, LeituraStatus.RELENDO);
        assertEquals(LeituraStatus.RELENDO, resultado.status());
    }

    @Test
    @DisplayName("Deve lancar excecao de LIDOS para LENDO")
    void deLidosParaLendo() {
        Leitura leitura = novaLeitura(LeituraStatus.LIVROS_LIDOS);
        assertThrows(LeituraEstadoInvalidoException.class,
                () -> useCase.execute(leitura, LeituraStatus.LIVROS_QUE_ESTOU_LENDO));
    }
}
