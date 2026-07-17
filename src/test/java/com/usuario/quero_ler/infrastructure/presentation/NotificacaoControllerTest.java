package com.usuario.quero_ler.infrastructure.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.usecases.notificacao.ListarNotificacoesPorUsuarioUseCase;
import com.usuario.quero_ler.core.usecases.notificacao.MarcarNotificacoesComoLidasUseCase;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.mapper.NotificacaoMapper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.notificacao.NotificacaoResponseDto;
import com.usuario.quero_ler.fixtures.NotificacaoFixture;
import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListarNotificacoesPorUsuarioUseCase listarNotificacoesPorUsuarioUseCase;

    @MockitoBean
    private MarcarNotificacoesComoLidasUseCase marcarNotificacoesComoLidasUseCase;

    @MockitoBean
    private NotificacaoMapper notificacaoMapper;

    @MockitoBean
    private UsuarioAtualHelper usuarioAtual;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar notificações não lidas do usuário")
    void deveRetornarNotificacoesNaoLidasDoUsuario() throws Exception {

        Notificacao notificacao = NotificacaoFixture.domain();
        UsuarioNotificacao usuarioNotificacao = usuarioNotificacao()
                .id(1L)
                .notificacao(notificacao)
                .visualizada(false)
                .build();

        NotificacaoResponseDto responseDto = NotificacaoFixture.response();

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        PaginatedResult<UsuarioNotificacao> result = new PaginatedResult<>(List.of(usuarioNotificacao), 1, 1, 0, 10);
        when(listarNotificacoesPorUsuarioUseCase.execute(any(Long.class), any(Pagination.class))).thenReturn(result);
        when(notificacaoMapper.toResponse(any(UsuarioNotificacao.class))).thenReturn(responseDto);

        mockMvc.perform(get("/notificacoes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(listarNotificacoesPorUsuarioUseCase).execute(any(Long.class), any(Pagination.class));
    }

    @Test
    @DisplayName("Deve marcar as notificações do usuario como lida")
    void deveMarcarNotificacoesComoLidas() throws Exception {
        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);

        mockMvc.perform(put("/notificacoes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(marcarNotificacoesComoLidasUseCase).execute(1L);
    }
}
