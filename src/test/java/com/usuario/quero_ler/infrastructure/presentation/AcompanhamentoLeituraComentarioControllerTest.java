package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoRequestDto;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;
import com.usuario.quero_ler.core.usecases.acompanhamento.AdicionarComentarioUseCase;
import com.usuario.quero_ler.infrastructure.mapper.AcompanhamentoMapper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcompanhamentoDeLeituraController.class)
@AutoConfigureMockMvc(addFilters = false)
class AcompanhamentoLeituraComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdicionarComentarioUseCase adicionarComentarioUseCase;

    @MockitoBean
    private AcompanhamentoMapper acompanhamentoMapper;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /leituras/{id}/comentarios deve retornar 201 quando sucesso")
    void postCriarComentarioSucesso() throws Exception {
        AcompanhamentoRequestDto dto = new AcompanhamentoRequestDto(1, 2, "coment");
        String json = objectMapper.writeValueAsString(dto);

        doNothing().when(adicionarComentarioUseCase).execute(any(Long.class), any());

        mockMvc.perform(post("/leituras/1/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /leituras/{id}/comentarios deve retornar 400 quando payload invalido ou diario nao existir")
    void postCriarComentarioBadRequest() throws Exception {
        AcompanhamentoRequestDto dto = new AcompanhamentoRequestDto(1, 2, "coment");
        String json = objectMapper.writeValueAsString(dto);

        doThrow(new DadosDiarioInvalidoException("Erro"))
                .when(adicionarComentarioUseCase).execute(any(Long.class), any());

        mockMvc.perform(post("/leituras/99/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
