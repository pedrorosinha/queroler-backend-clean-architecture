package com.usuario.quero_ler.core.usecases.diario.impl;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.exceptions.DiarioNaoEncontradoException;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;
import com.usuario.quero_ler.core.exceptions.UsuarioSemPermissaoParaAcaoException;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@DisplayName("AtualizarDiarioUseCase")
class AtualizarDiarioUseCaseImplTest {

    @Mock
    private DiarioDeLeituraGateway diarioGateway;
    @Mock
    private LeituraGateway leituraGateway;
    @Mock
    private ControleStatusLeituraUseCase controleStatusUseCase;
    @InjectMocks
    private AtualizarDiarioUseCaseImpl useCase;

    private Leitura leituraDoUsuario(Long usuarioId) {
        return leitura().id(1L).status(LeituraStatus.LIVROS_QUE_ESTOU_LENDO)
                .usuario(usuario().id(usuarioId).build()).build();
    }

    private DiarioDeLeitura diarioExistente(Leitura leitura) {
        return diarioDeLeitura()
                .id(5L)
                .leitura(leitura)
                .inicioDaLeitura(LocalDateTime.now().minusDays(10))
                .paginasLidas(50)
                .nota(3.0)
                .build();
    }

    @Test
    @DisplayName("Deve atualizar campos do diario")
    void atualizarCampos() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura()
                .paginasLidas(200)
                .nota(5.0)
                .tituloDaResenha("Novo titulo")
                .resenha("Nova resenha")
                .build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        useCase.execute(5L, dados, 10L);

        verify(diarioGateway).save(argThat(d ->
                d.paginasLidas() == 200 && d.nota() == 5.0
                        && "Novo titulo".equals(d.tituloDaResenha())
                        && "Nova resenha".equals(d.resenha())));
    }

    @Test
    @DisplayName("Deve alterar status para LIDOS quando terminoDaLeitura e informado")
    void atualizarComTermino() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura()
                .terminoDaLeitura(LocalDateTime.now().minusHours(1))
                .build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));
        when(controleStatusUseCase.execute(any(), eq(LeituraStatus.LIVROS_LIDOS)))
                .thenReturn(leitura);

        useCase.execute(5L, dados, 10L);

        verify(controleStatusUseCase).execute(argThat(l -> l.id().equals(1L)), eq(LeituraStatus.LIVROS_LIDOS));
        verify(leituraGateway).save(leitura);
    }

    @Test
    @DisplayName("Deve lancar excecao quando diario nao existe")
    void diarioNaoEncontrado() {
        when(diarioGateway.findById(99L)).thenReturn(Optional.empty());
        DiarioDeLeitura dados = diarioDeLeitura().paginasLidas(100).build();

        assertThrows(DiarioNaoEncontradoException.class,
                () -> useCase.execute(99L, dados, 10L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando usuario nao e proprietario")
    void usuarioSemPermissao() {
        Leitura leitura = leituraDoUsuario(99L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura().paginasLidas(100).build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        assertThrows(UsuarioSemPermissaoParaAcaoException.class,
                () -> useCase.execute(5L, dados, 10L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando inicio e futuro")
    void inicioFuturo() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura()
                .inicioDaLeitura(LocalDateTime.now().plusDays(1)).build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        assertThrows(DadosDiarioInvalidoException.class,
                () -> useCase.execute(5L, dados, 10L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando termino e anterior a inicio efetivo")
    void terminoAnteriorInicio() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura()
                .terminoDaLeitura(LocalDateTime.now().minusDays(20))
                .build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        assertThrows(DadosDiarioInvalidoException.class,
                () -> useCase.execute(5L, dados, 10L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando paginasLidas e negativa")
    void paginasNegativas() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura().paginasLidas(-1).build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        assertThrows(DadosDiarioInvalidoException.class,
                () -> useCase.execute(5L, dados, 10L));
    }

    @Test
    @DisplayName("Deve lancar excecao quando nota e invalida")
    void notaInvalida() {
        Leitura leitura = leituraDoUsuario(10L);
        DiarioDeLeitura existente = diarioExistente(leitura);
        DiarioDeLeitura dados = diarioDeLeitura().nota(6.0).build();

        when(diarioGateway.findById(5L)).thenReturn(Optional.of(existente));

        assertThrows(DadosDiarioInvalidoException.class,
                () -> useCase.execute(5L, dados, 10L));
    }
}
