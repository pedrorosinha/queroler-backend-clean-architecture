package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.ApagarNotificacoesAntigasUseCase;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
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
@DisplayName("ListarNotificacoesPorUsuarioUseCase")
class ListarNotificacoesPorUsuarioUseCaseImplTest {

    @Mock
    private UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    @Mock
    private ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase;
    @InjectMocks
    private ListarNotificacoesPorUsuarioUseCaseImpl useCase;

    @Test
    @DisplayName("Deve listar notificacoes do usuario com paginacao")
    void listarComPaginacao() {
        Pagination pagination = new Pagination(0, 10);
        UsuarioNotificacao not1 = usuarioNotificacao().id(1L).build();
        UsuarioNotificacao not2 = usuarioNotificacao().id(2L).build();

        PaginatedResult<UsuarioNotificacao> pageResult = new PaginatedResult<>(List.of(not1, not2), 2, 1, 0, 10);
        when(usuarioNotificacaoGateway.buscarTodasPorUsuario(1L, pagination)).thenReturn(pageResult);

        PaginatedResult<UsuarioNotificacao> resultado = useCase.execute(1L, pagination);

        verify(apagarNotificacoesAntigasUseCase).execute();
        assertEquals(2, resultado.content().size());
        assertEquals(2, resultado.totalElements());
    }

    @Test
    @DisplayName("Deve chamar cleanup antes de listar")
    void cleanupChamado() {
        Pagination pagination = new Pagination(0, 10);
        PaginatedResult<UsuarioNotificacao> emptyResult = new PaginatedResult<>(List.of(), 0, 0, 0, 10);
        when(usuarioNotificacaoGateway.buscarTodasPorUsuario(1L, pagination)).thenReturn(emptyResult);

        useCase.execute(1L, pagination);

        verify(apagarNotificacoesAntigasUseCase).execute();
    }

    @Test
    @DisplayName("Deve retornar pagina vazia quando usuario nao tem notificacoes")
    void paginaVazia() {
        Pagination pagination = new Pagination(0, 10);
        PaginatedResult<UsuarioNotificacao> emptyResult = new PaginatedResult<>(List.of(), 0, 0, 0, 10);
        when(usuarioNotificacaoGateway.buscarTodasPorUsuario(99L, pagination)).thenReturn(emptyResult);

        PaginatedResult<UsuarioNotificacao> resultado = useCase.execute(99L, pagination);

        assertTrue(resultado.content().isEmpty());
    }
}
