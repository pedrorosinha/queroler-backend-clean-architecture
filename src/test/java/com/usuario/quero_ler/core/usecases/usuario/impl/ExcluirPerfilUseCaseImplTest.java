package com.usuario.quero_ler.core.usecases.usuario.impl;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.exceptions.UsuarioSemPermissaoParaAcaoException;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExcluirPerfilUseCase")
class ExcluirPerfilUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;
    @Mock
    private UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    @InjectMocks
    private ExcluirPerfilUseCaseImpl useCase;

    @Test
    @DisplayName("Deve excluir perfil de usuario LEITOR e suas notificacoes")
    void excluirLeitor() {
        Usuario usuario = usuario().id(1L).build();
        UsuarioNotificacao not1 = usuarioNotificacao().id(10L).build();
        UsuarioNotificacao not2 = usuarioNotificacao().id(11L).build();

        when(usuarioNotificacaoGateway.findByUsuarioId(1L)).thenReturn(List.of(not1, not2));

        useCase.execute(usuario, UsuarioProfile.LEITOR);

        verify(usuarioNotificacaoGateway).delete(not1);
        verify(usuarioNotificacaoGateway).delete(not2);
        verify(usuarioGateway).delete(usuario);
    }

    @Test
    @DisplayName("Deve excluir perfil quando usuario nao tem notificacoes")
    void excluirSemNotificacoes() {
        Usuario usuario = usuario().id(2L).build();
        when(usuarioNotificacaoGateway.findByUsuarioId(2L)).thenReturn(List.of());

        useCase.execute(usuario, UsuarioProfile.LEITOR);

        verify(usuarioGateway).delete(usuario);
        verify(usuarioNotificacaoGateway, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lancar excecao para perfil ADMINISTRADOR")
    void administradorNaoPodeExcluir() {
        Usuario usuario = usuario().id(1L).build();
        assertThrows(UsuarioSemPermissaoParaAcaoException.class,
                () -> useCase.execute(usuario, UsuarioProfile.ADMINISTRADOR));
        verify(usuarioGateway, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lancar excecao para perfil MODERADOR")
    void moderadorNaoPodeExcluir() {
        Usuario usuario = usuario().id(1L).build();
        assertThrows(UsuarioSemPermissaoParaAcaoException.class,
                () -> useCase.execute(usuario, UsuarioProfile.MODERADOR));
        verify(usuarioGateway, never()).delete(any());
    }
}
