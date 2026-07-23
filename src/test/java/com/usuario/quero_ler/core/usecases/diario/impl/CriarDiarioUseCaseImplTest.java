package com.usuario.quero_ler.core.usecases.diario.impl;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.exceptions.DiarioJaExisteException;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;
import com.usuario.quero_ler.core.exceptions.LeituraNaoEncontradaException;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarDiarioUseCase")
class CriarDiarioUseCaseImplTest {

    @Mock
    private DiarioDeLeituraGateway diarioGateway;
    @Mock
    private LeituraGateway leituraGateway;
    @Mock
    private ControleStatusLeituraUseCase controleStatusUseCase;
    @InjectMocks
    private CriarDiarioUseCaseImpl useCase;

    @Captor
    private ArgumentCaptor<DiarioDeLeitura> diarioCaptor;
    @Captor
    private ArgumentCaptor<Leitura> leituraCaptor;

    private Leitura leituraExistente() {
        return leitura().id(1L).status(LeituraStatus.LIVROS_QUE_QUERO_LER)
                .usuario(usuario().id(10L).build()).build();
    }

    private DiarioDeLeitura diarioValido(LocalDateTime inicio, LocalDateTime termino) {
        return diarioDeLeitura()
                .inicioDaLeitura(inicio)
                .terminoDaLeitura(termino)
                .paginasLidas(100)
                .tituloDaResenha("Minha resenha")
                .resenha("Texto da resenha")
                .build();
    }

    @Test
    @DisplayName("Deve criar diario com termino preenchido e alterar status para LIDOS")
    void criarDiarioComTermino() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(10);
        LocalDateTime termino = LocalDateTime.now().minusHours(1);
        DiarioDeLeitura diario = diarioDeLeitura()
                .inicioDaLeitura(inicio)
                .terminoDaLeitura(termino)
                .paginasLidas(100)
                .nota(4.5)
                .tituloDaResenha("Minha resenha")
                .resenha("Texto da resenha")
                .build();
        Leitura leitura = leituraExistente();

        when(leituraGateway.findByUsuarioIdAndLivroId(10L, 1L)).thenReturn(Optional.of(leitura));
        when(controleStatusUseCase.execute(any(), eq(LeituraStatus.LIVROS_LIDOS))).thenReturn(leitura);
        when(diarioGateway.existsByLeitura(any())).thenReturn(false);

        useCase.execute(diario, 10L, 1L);

        verify(controleStatusUseCase).execute(eq(leitura), eq(LeituraStatus.LIVROS_LIDOS));
        verify(leituraGateway).save(leitura);
        verify(diarioGateway).save(diarioCaptor.capture());
        DiarioDeLeitura salvo = diarioCaptor.getValue();
        assertEquals(inicio, salvo.inicioDaLeitura());
        assertEquals(termino, salvo.terminoDaLeitura());
        assertEquals(100, salvo.paginasLidas());
        assertEquals(4.5, salvo.nota());
        assertFalse(salvo.spoiler());
    }

    @Test
    @DisplayName("Deve criar diario sem termino e alterar status para LENDO")
    void criarDiarioSemTermino() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(5);
        DiarioDeLeitura diario = diarioValido(inicio, null);
        Leitura leitura = leituraExistente();

        when(leituraGateway.findByUsuarioIdAndLivroId(10L, 1L)).thenReturn(Optional.of(leitura));
        when(controleStatusUseCase.execute(any(), eq(LeituraStatus.LIVROS_QUE_ESTOU_LENDO))).thenReturn(leitura);
        when(diarioGateway.existsByLeitura(any())).thenReturn(false);

        useCase.execute(diario, 10L, 1L);

        verify(controleStatusUseCase).execute(eq(leitura), eq(LeituraStatus.LIVROS_QUE_ESTOU_LENDO));
        verify(diarioGateway).save(diarioCaptor.capture());
        assertEquals(0.0, diarioCaptor.getValue().nota());
        assertFalse(diarioCaptor.getValue().spoiler());
    }

    @Test
    @DisplayName("Deve lancar excecao quando diario ja existe")
    void diarioJaExiste() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(5);
        DiarioDeLeitura diario = diarioValido(inicio, null);
        Leitura leitura = leituraExistente();

        when(leituraGateway.findByUsuarioIdAndLivroId(10L, 1L)).thenReturn(Optional.of(leitura));
        when(controleStatusUseCase.execute(any(), any())).thenReturn(leitura);
        when(diarioGateway.existsByLeitura(any())).thenReturn(true);

        assertThrows(DiarioJaExisteException.class, () -> useCase.execute(diario, 10L, 1L));
        verify(diarioGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando leitura nao e encontrada")
    void leituraNaoEncontrada() {
        DiarioDeLeitura diario = diarioValido(LocalDateTime.now().minusDays(1), null);
        when(leituraGateway.findByUsuarioIdAndLivroId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(LeituraNaoEncontradaException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando inicioDaLeitura e nulo")
    void inicioNulo() {
        DiarioDeLeitura diario = diarioDeLeitura().build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando inicioDaLeitura e futuro")
    void inicioFuturo() {
        LocalDateTime futuro = LocalDateTime.now().plusDays(1);
        DiarioDeLeitura diario = diarioDeLeitura().inicioDaLeitura(futuro).build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando termino e anterior a inicio")
    void terminoAnteriorInicio() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(5);
        LocalDateTime termino = LocalDateTime.now().minusDays(10);
        DiarioDeLeitura diario = diarioDeLeitura()
                .inicioDaLeitura(inicio).terminoDaLeitura(termino).build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando paginasLidas e negativa")
    void paginasNegativas() {
        DiarioDeLeitura diario = diarioDeLeitura()
                .inicioDaLeitura(LocalDateTime.now().minusDays(1))
                .paginasLidas(-5).build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando nota e menor que 0")
    void notaInvalida() {
        DiarioDeLeitura diario = diarioDeLeitura()
                .inicioDaLeitura(LocalDateTime.now().minusDays(1))
                .nota(-1.0).build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando nota e maior que 5")
    void notaAcimaLimite() {
        DiarioDeLeitura diario = diarioDeLeitura()
                .inicioDaLeitura(LocalDateTime.now().minusDays(1))
                .nota(6.0).build();
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(diario, 10L, 1L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando payload e nulo")
    void payloadNulo() {
        assertThrows(DadosDiarioInvalidoException.class, () -> useCase.execute(null, 10L, 1L));
    }
}
