package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.ApagarNotificacoesAntigasUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MarcarNotificacoesComoLidasUseCase")
class MarcarNotificacoesComoLidasUseCaseImplTest {

    @Mock
    private UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    @Mock
    private ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase;
    @InjectMocks
    private MarcarNotificacoesComoLidasUseCaseImpl useCase;

    @Test
    @DisplayName("Deve chamar cleanup antes de marcar como lidas")
    void cleanupChamadoAntes() {
        useCase.execute(1L);

        verify(apagarNotificacoesAntigasUseCase).execute();
        verify(usuarioNotificacaoGateway).marcarComoLidas(1L);
    }

    @Test
    @DisplayName("Deve marcar notificacoes como lidas para o usuario especifico")
    void marcarParaUsuarioEspecifico() {
        useCase.execute(42L);

        verify(usuarioNotificacaoGateway).marcarComoLidas(42L);
    }
}
