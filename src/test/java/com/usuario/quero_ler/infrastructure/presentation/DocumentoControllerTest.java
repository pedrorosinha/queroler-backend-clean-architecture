package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoAlteracoesDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoRequestDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoResponseDto;
import com.usuario.quero_ler.fixtures.DocumentoFixture;
import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import com.usuario.quero_ler.core.usecases.documento.CriarDocumentoUseCase;
import com.usuario.quero_ler.core.usecases.documento.BuscarTermosGeraisDeUsoUseCase;
import com.usuario.quero_ler.core.usecases.documento.AlterarDocumentoUseCase;
import com.usuario.quero_ler.infrastructure.mapper.DocumentoMapper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(DocumentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class DocumentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarDocumentoUseCase criarDocumentoUseCase;

    @MockitoBean
    private BuscarTermosGeraisDeUsoUseCase buscarTermosGeraisDeUsoUseCase;

    @MockitoBean
    private AlterarDocumentoUseCase alterarDocumentoUseCase;

    @MockitoBean
    private DocumentoMapper documentoMapper;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar documento com sucesso")
    void deveCriarDocumento() throws Exception {
        DocumentoRequestDto request = DocumentoFixture.requestDto();
        var doc = documento()
                .id(2L).titulo("TERMOS_GERAIS_DE_USO")
                .tipo(com.usuario.quero_ler.core.enums.DocumentoTipo.TERMOS_GERAIS_DE_USO)
                .conteudo("Conteúdo do documento")
                .ultimaAlteracao(java.time.LocalDateTime.now()).build();
        DocumentoResponseDto response = DocumentoFixture.responseDto();

        when(documentoMapper.toDomain(any(DocumentoRequestDto.class))).thenReturn(doc);
        when(criarDocumentoUseCase.execute(any())).thenReturn(doc);
        when(documentoMapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(post("/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.titulo").value(response.titulo()));
    }

    @Test
    @DisplayName("Deve retornar termos gerais de uso")
    void deveRetornarTermosGerais() throws Exception {
        var doc = documento()
                .id(2L).titulo("TERMOS_GERAIS_DE_USO")
                .tipo(com.usuario.quero_ler.core.enums.DocumentoTipo.TERMOS_GERAIS_DE_USO)
                .conteudo("Conteúdo do documento")
                .ultimaAlteracao(java.time.LocalDateTime.now()).build();
        DocumentoResponseDto response = DocumentoFixture.responseDto();

        when(buscarTermosGeraisDeUsoUseCase.execute()).thenReturn(doc);
        when(documentoMapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(get("/documentos/termos-gerais-de-uso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.titulo").value(response.titulo()))
                .andExpect(jsonPath("$.conteudo").value(response.conteudo()));
    }

    @Test
    @DisplayName("Deve alterar documento com sucesso")
    void deveAlterarDocumento() throws Exception {
        DocumentoAlteracoesDto dto = new DocumentoAlteracoesDto(
                "Titulo novo", null, "Conteudo novo"
        );

        mockMvc.perform(put("/documentos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(alterarDocumentoUseCase).execute(eq(1L), any());
    }
}
