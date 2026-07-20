package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraRequestDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraAtualizadoRequest;
import com.usuario.quero_ler.fixtures.DiarioLeituraFixtures;
import com.usuario.quero_ler.core.exceptions.DiarioNaoEncontradoException;
import com.usuario.quero_ler.core.exceptions.LeituraNaoEncontradaException;
import com.usuario.quero_ler.core.exceptions.UsuarioSemPermissaoParaAcaoException;
import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.usecases.diario.CriarDiarioUseCase;
import com.usuario.quero_ler.core.usecases.diario.BuscarDiarioPorLivroEUsuarioUseCase;
import com.usuario.quero_ler.core.usecases.diario.AtualizarDiarioUseCase;
import com.usuario.quero_ler.infrastructure.mapper.DiarioLeituraMapper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DiarioDeLeituraController.class)
@AutoConfigureMockMvc(addFilters = false)
class DiarioDeLeituraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarDiarioUseCase criarDiarioUseCase;

    @MockitoBean
    private BuscarDiarioPorLivroEUsuarioUseCase buscarDiarioPorLivroEUsuarioUseCase;

    @MockitoBean
    private AtualizarDiarioUseCase atualizarDiarioUseCase;

    @MockitoBean
    private DiarioLeituraMapper diarioLeituraMapper;

    @MockitoBean
    private UsuarioAtualHelper usuarioAtual;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /diario deve retornar 201 quando criar com sucesso")
    void postCriarSucesso() throws Exception {
        DiarioDeLeituraRequestDto requestDto = DiarioLeituraFixtures.novoDiarioDeLeitura();
        String json = objectMapper.writeValueAsString(requestDto);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(diarioLeituraMapper.toDomain(any(DiarioDeLeituraRequestDto.class)))
                .thenReturn(DiarioLeituraFixtures.diarioDeLeitura());
        doNothing().when(criarDiarioUseCase).execute(any(), any(Long.class), any(Long.class));

        mockMvc.perform(post("/diario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /diario deve retornar 404 quando usuarioLivro não existir")
    void postCriarNotFound() throws Exception {
        DiarioDeLeituraRequestDto requestDto = DiarioLeituraFixtures.novoDiarioDeLeitura();
        String json = objectMapper.writeValueAsString(requestDto);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(diarioLeituraMapper.toDomain(any(DiarioDeLeituraRequestDto.class)))
                .thenReturn(DiarioLeituraFixtures.diarioDeLeitura());
        doThrow(new LeituraNaoEncontradaException("Não encontrado"))
                .when(criarDiarioUseCase).execute(any(), any(Long.class), any(Long.class));

        mockMvc.perform(post("/diario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /diario deve retornar 200 e o json com os dados do Diario de leitura.")
    void deveRetornarODiarioDeLeituraComStatus200() throws Exception {
        DiarioDeLeitura diario = DiarioLeituraFixtures.diarioDeLeitura();
        var responseDto = DiarioLeituraFixtures.diarioDeLeituraResponse();

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(buscarDiarioPorLivroEUsuarioUseCase.execute(1L, 2L)).thenReturn(diario);
        when(diarioLeituraMapper.toResponse(any())).thenReturn(responseDto);

        mockMvc.perform(get("/diario")
                .param("livroId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.tituloDaResenha").value(responseDto.tituloDaResenha()))
                .andExpect(jsonPath("$.resenha").value(responseDto.resenha()))
                .andExpect(jsonPath("$.spoilers").value(responseDto.spoilers()));
    }

    @Test
    @DisplayName("GET /diario deve retornar 404.")
    void deveLancarExcecaoDiarioDeLeituraNaoEncontrado404() throws Exception {
        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        doThrow(new DiarioNaoEncontradoException("Diario não encontrado!"))
                .when(buscarDiarioPorLivroEUsuarioUseCase).execute(any(Long.class), any(Long.class));

        mockMvc.perform(get("/diario")
                .param("livroId", "99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /diario/{id} deve retornar 204 quando atualizar com sucesso")
    void putAtualizarSucesso() throws Exception {
        DiarioDeLeituraRequestDto requestDto = DiarioLeituraFixtures.novoDiarioDeLeitura();
        DiarioDeLeituraAtualizadoRequest updateDto = new DiarioDeLeituraAtualizadoRequest(
                requestDto.inicioDaLeitura(),
                requestDto.terminoDaLeitura(),
                requestDto.paginasLidas(),
                requestDto.nota(),
                requestDto.tituloDaResenha(),
                requestDto.resenha());

        String json = objectMapper.writeValueAsString(updateDto);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(diarioLeituraMapper.toDomain(any(DiarioDeLeituraAtualizadoRequest.class)))
                .thenReturn(DiarioLeituraFixtures.diarioDeLeitura());
        doNothing().when(atualizarDiarioUseCase).execute(any(Long.class), any(), any(Long.class));

        mockMvc.perform(put("/diario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /diario/{id} deve retornar 404 quando diário não existir")
    void putAtualizarNotFound() throws Exception {
        DiarioDeLeituraRequestDto requestDto = DiarioLeituraFixtures.novoDiarioDeLeitura();
        DiarioDeLeituraAtualizadoRequest updateDto = new DiarioDeLeituraAtualizadoRequest(
                requestDto.inicioDaLeitura(),
                requestDto.terminoDaLeitura(),
                requestDto.paginasLidas(),
                requestDto.nota(),
                requestDto.tituloDaResenha(),
                requestDto.resenha());

        String json = objectMapper.writeValueAsString(updateDto);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(diarioLeituraMapper.toDomain(any(DiarioDeLeituraAtualizadoRequest.class)))
                .thenReturn(DiarioLeituraFixtures.diarioDeLeitura());
        doThrow(new DiarioNaoEncontradoException("Não encontrado"))
                .when(atualizarDiarioUseCase).execute(any(Long.class), any(), any(Long.class));

        mockMvc.perform(put("/diario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /diario/{id} deve retornar 409 quando usuário não tiver permissão")
    void putAtualizarSemPermissao() throws Exception {
        DiarioDeLeituraRequestDto requestDto = DiarioLeituraFixtures.novoDiarioDeLeitura();
        DiarioDeLeituraAtualizadoRequest updateDto = new DiarioDeLeituraAtualizadoRequest(
                requestDto.inicioDaLeitura(),
                requestDto.terminoDaLeitura(),
                requestDto.paginasLidas(),
                requestDto.nota(),
                requestDto.tituloDaResenha(),
                requestDto.resenha());

        String json = objectMapper.writeValueAsString(updateDto);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(diarioLeituraMapper.toDomain(any(DiarioDeLeituraAtualizadoRequest.class)))
                .thenReturn(DiarioLeituraFixtures.diarioDeLeitura());
        doThrow(new UsuarioSemPermissaoParaAcaoException("Sem permissão"))
                .when(atualizarDiarioUseCase).execute(any(Long.class), any(), any(Long.class));

        mockMvc.perform(put("/diario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict());
    }
}
