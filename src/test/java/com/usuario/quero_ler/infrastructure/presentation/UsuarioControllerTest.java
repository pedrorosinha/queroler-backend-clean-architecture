package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.usuario.*;
import com.usuario.quero_ler.fixtures.UserFixture;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.usecases.usuario.*;
import com.usuario.quero_ler.core.usecases.user.CriarUserUseCase;
import com.usuario.quero_ler.core.usecases.user.AlterarSenhaUseCase;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorUsuarioUseCase;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.mapper.UsuarioMapper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarUserUseCase criarUserUseCase;

    @MockitoBean
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @MockitoBean
    private BuscarFotoUsuarioUseCase buscarFotoUsuarioUseCase;

    @MockitoBean
    private AtualizarDadosAdicionaisUseCase atualizarDadosAdicionaisUseCase;

    @MockitoBean
    private AtualizarDadosLeitorUseCase atualizarDadosLeitorUseCase;

    @MockitoBean
    private AtualizarDadosAdministradorUseCase atualizarDadosAdministradorUseCase;

    @MockitoBean
    private AlterarSenhaUseCase alterarSenhaUseCase;

    @MockitoBean
    private ExcluirPerfilUseCase excluirPerfilUseCase;

    @MockitoBean
    private ListarAcompanhamentoPorUsuarioUseCase listarAcompanhamentoPorUsuarioUseCase;

    @MockitoBean
    private UsuarioMapper usuarioMapper;

    @MockitoBean
    private UsuarioAtualHelper usuarioAtual;

    @MockitoBean
    private Validator validator;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um Usuário com sucesso")
    void deveCriarUmUsuarioComSucesso() throws Exception {
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);
        UsuarioRequestDto request = UserFixture.requestDto();
        Usuario usuario = UserFixture.entidadeCompleta(user);
        UsuarioResponseDto response = UserFixture.response(usuario);

        when(usuarioMapper.toDomain(any(UsuarioRequestDto.class))).thenReturn(usuario);
        when(usuarioMapper.toResponse(any(Usuario.class))).thenReturn(response);
        when(criarUserUseCase.execute(any(), any(), any())).thenReturn(user);
        when(criarUsuarioUseCase.execute(any(Usuario.class))).thenReturn(usuario);

        MockMultipartFile dados = new MockMultipartFile(
                "dados", "", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/usuarios")
                        .file(dados)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }

    @Test
    @DisplayName("Deve retornar dados do usuário")
    void deveRetornarDadosDoUsuario() throws Exception {
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);
        Usuario usuario = UserFixture.entidadeCompleta(user);
        user = user.withUsuario(usuario);
        UsuarioResponseDto response = UserFixture.response(usuario);

        when(usuarioAtual.getUsuarioAtual()).thenReturn(usuario);
        when(usuarioMapper.toResponse(any(Usuario.class))).thenReturn(response);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()));
    }

    @Test
    @DisplayName("Deve alterar a senha do usuário com sucesso")
    void deveAlterarASenhaDoUsuarioComSucesso() throws Exception {
        UsuarioAlterarSenhaRequest request = new UsuarioAlterarSenhaRequest("Teste123&", "Senha1232@");
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);

        when(usuarioAtual.getUsuarioLogado()).thenReturn(user);

        mockMvc.perform(put("/usuarios/alterar-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(alterarSenhaUseCase).execute(user, "Teste123&", "Senha1232@");
    }

    @Test
    @DisplayName("Deve apagar um perfil com sucesso")
    void deveApagarUmPerfilComSucesso() throws Exception {
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);
        Usuario usuario = UserFixture.entidadeCompleta(user);

        when(usuarioAtual.getUsuarioAtual()).thenReturn(usuario);
        when(usuarioAtual.getUsuarioLogado()).thenReturn(user);

        mockMvc.perform(delete("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(excluirPerfilUseCase).execute(any(Usuario.class), any());
    }
}
