package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.enums.LivroIdioma;
import com.usuario.quero_ler.fixtures.LivroFixture;
import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import com.usuario.quero_ler.core.usecases.livro.*;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorLivroUseCase;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.mapper.LivroMapper;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarLivroUseCase criarLivroUseCase;

    @MockitoBean
    private InserirCapaLivroUseCase inserirCapaLivroUseCase;

    @MockitoBean
    private BuscarLivroUseCase buscarLivroUseCase;

    @MockitoBean
    private BuscarCapaLivroUseCase buscarCapaLivroUseCase;

    @MockitoBean
    private BuscarLivrosComFiltrosUseCase buscarLivrosComFiltrosUseCase;

    @MockitoBean
    private ListarLivrosTelaLeituraUseCase listarLivrosTelaLeituraUseCase;

    @MockitoBean
    private ListarLivrosPopularesUseCase listarLivrosPopularesUseCase;

    @MockitoBean
    private ListarLivrosDetalhadosUseCase listarLivrosDetalhadosUseCase;

    @MockitoBean
    private BuscarLivroPorIsbnUseCase buscarLivroPorIsbnUseCase;

    @MockitoBean
    private ListarAcompanhamentoPorLivroUseCase listarAcompanhamentoPorLivroUseCase;

    @MockitoBean
    private LivroMapper livroMapper;

    @MockitoBean
    private SecurityFilter securityFilter;

    @MockitoBean
    private UsuarioAtualHelper usuarioAtual;

    @MockitoBean
    private jakarta.validation.Validator validator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve cadastrar um novo livro sem capa com sucesso")
    void deveCadastrarUmNovoLivroSemCapa() throws Exception {
        com.usuario.quero_ler.infrastructure.dto.livro.LivroRequest dto = LivroFixture.request();
        String json = objectMapper.writeValueAsString(dto);

        MockMultipartFile dados = new MockMultipartFile(
                "dados", "", "application/json", json.getBytes());

        Livro livro = livro().id(1L).titulo("Clean Code").isbn("9780132350884")
                .editora("Prentice Hall").anoDePublicacao(Year.of(2008)).numeroDePaginas(464)
                .idioma(LivroIdioma.PORTUGUES).sinopse("sinopse").build();
        when(criarLivroUseCase.execute(any(Livro.class), any(), isNull())).thenReturn(livro);
        when(validator.validate(any())).thenReturn(Set.of());

        mockMvc.perform(multipart("/livros").file(dados))
                .andExpect(status().isCreated());

        verify(criarLivroUseCase).execute(any(Livro.class), any(), isNull());
    }

    @Test
    @DisplayName("Deve retornar livros do usuário para tela de leitura com sucesso")
    void deveRetornarLivrosTelaDeLeituraDoUsuario() throws Exception {
        Livro livro = livro().id(1L).titulo("Clean Code").build();
        PaginatedResult<Livro> result = new PaginatedResult<>(List.of(livro), 1, 1, 0, 10);

        when(usuarioAtual.getUsuarioAtualId()).thenReturn(1L);
        when(listarLivrosTelaLeituraUseCase.execute(anyLong(), any(Pagination.class))).thenReturn(result);

        mockMvc.perform(get("/livros/tela_de_leitura")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0]").exists());

        verify(listarLivrosTelaLeituraUseCase).execute(anyLong(), any(Pagination.class));
    }
}
