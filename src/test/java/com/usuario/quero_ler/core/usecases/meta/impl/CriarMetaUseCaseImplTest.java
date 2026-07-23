package com.usuario.quero_ler.core.usecases.meta.impl;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.exceptions.DataInvalidaException;
import com.usuario.quero_ler.core.exceptions.MetaDeLeituraJaCadastradaException;
import com.usuario.quero_ler.core.gateway.MetaLeituraGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarMetaUseCase")
class CriarMetaUseCaseImplTest {

    @Mock
    private MetaLeituraGateway metaLeituraGateway;
    @InjectMocks
    private CriarMetaUseCaseImpl useCase;

    @Captor
    private ArgumentCaptor<MetaLeitura> metaCaptor;

    private Usuario usuarioPadrao() {
        return usuario().id(1L).build();
    }

    @Test
    @DisplayName("Deve criar meta para o ano atual quando ano e nulo")
    void criarMetaAnoAtual() {
        MetaLeitura meta = metaLeitura().ano(null).metaLivrosAno(20).build();
        when(metaLeituraGateway.existsByUsuarioAndAno(any(), eq(LocalDate.now().getYear()))).thenReturn(false);
        when(metaLeituraGateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(meta, usuarioPadrao());

        verify(metaLeituraGateway).save(metaCaptor.capture());
        assertEquals(20, metaCaptor.getValue().metaLivrosAno());
        assertEquals(1L, metaCaptor.getValue().usuario().id());
    }

    @Test
    @DisplayName("Deve criar meta para ano futuro")
    void criarMetaAnoFuturo() {
        Integer anoFuturo = LocalDate.now().getYear() + 1;
        MetaLeitura meta = metaLeitura().ano(anoFuturo).metaLivrosAno(15).build();
        when(metaLeituraGateway.existsByUsuarioAndAno(any(), eq(anoFuturo))).thenReturn(false);
        when(metaLeituraGateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        MetaLeitura resultado = useCase.execute(meta, usuarioPadrao());

        assertEquals(anoFuturo, resultado.ano());
    }

    @Test
    @DisplayName("Deve lancar excecao quando ano e anterior ao atual")
    void anoPassado() {
        MetaLeitura meta = metaLeitura().ano(LocalDate.now().getYear() - 1).metaLivrosAno(10).build();

        assertThrows(DataInvalidaException.class, () -> useCase.execute(meta, usuarioPadrao()));
        verify(metaLeituraGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando meta ja existe para o ano")
    void metaDuplicada() {
        MetaLeitura meta = metaLeitura().ano(LocalDate.now().getYear()).metaLivrosAno(10).build();
        when(metaLeituraGateway.existsByUsuarioAndAno(any(), eq(LocalDate.now().getYear()))).thenReturn(true);

        assertThrows(MetaDeLeituraJaCadastradaException.class, () -> useCase.execute(meta, usuarioPadrao()));
        verify(metaLeituraGateway, never()).save(any());
    }
}
