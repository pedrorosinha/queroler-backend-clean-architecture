package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.meta.MetaRequestDto;
import com.usuario.quero_ler.fixtures.MetaLeituraFixture;
import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.usecases.meta.CriarMetaUseCase;
import com.usuario.quero_ler.core.usecases.meta.DeletarMetasUseCase;
import com.usuario.quero_ler.infrastructure.mapper.MetaLeituraMapper;
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

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MetaLeituraControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarMetaUseCase criarMetaUseCase;

    @MockitoBean
    private DeletarMetasUseCase deletarMetasUseCase;

    @MockitoBean
    private MetaLeituraMapper metaLeituraMapper;

    @MockitoBean
    private UsuarioAtualHelper usuarioAtual;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve adicionar uma meta de leitura com sucesso")
    void deveCriarMeta() throws Exception {
        Integer proximoAno = LocalDate.now().plusYears(1).getYear();
        MetaRequestDto dto = MetaLeituraFixture.requestDto(proximoAno);

        Usuario usuario = com.usuario.quero_ler.fixtures.UserFixture.entidadeCompleta();
        MetaLeitura meta = metaLeitura()
                .ano(proximoAno).metaLivrosAno(12).metaLivrosMes(1).metaPaginasDia(30).build();

        when(usuarioAtual.getUsuarioAtual()).thenReturn(usuario);
        when(metaLeituraMapper.toDomain(any(MetaRequestDto.class))).thenReturn(meta);

        mockMvc.perform(post("/metas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(criarMetaUseCase).execute(any(MetaLeitura.class), any(Usuario.class));
    }

    @Test
    @DisplayName("Deve deletar todas as metas de leitura do usuario")
    void deveDeltarMetasDoUsuario() throws Exception {
        Usuario usuario = com.usuario.quero_ler.fixtures.UserFixture.entidadeCompleta();

        when(usuarioAtual.getUsuarioAtual()).thenReturn(usuario);
        doNothing().when(deletarMetasUseCase).execute(any(Usuario.class));

        mockMvc.perform(delete("/metas"))
                .andExpect(status().isNoContent());

        verify(deletarMetasUseCase).execute(any(Usuario.class));
    }
}
