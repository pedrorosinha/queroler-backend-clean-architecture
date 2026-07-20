package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.infrastructure.dto.login.LoginRequestDto;
import com.usuario.quero_ler.fixtures.LoginFixture;
import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import com.usuario.quero_ler.core.usecases.user.LoginUseCase;
import com.usuario.quero_ler.infrastructure.security.SecurityFilter;
import com.usuario.quero_ler.infrastructure.security.TokenService;
import com.usuario.quero_ler.core.entities.LoginResult;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginUseCase loginUseCase;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar um login com sucesso")
    void deveRealizarLoginComSucesso() throws Exception {
        LoginRequestDto request = LoginFixture.requestDto();

        User user = user().id(1L).user("admin").profile(UsuarioProfile.LEITOR).build();
        LoginResult loginResult = new LoginResult(user, false);

        when(loginUseCase.execute(any(), any())).thenReturn(loginResult);
        when(tokenService.generateToken(any())).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/logins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(loginUseCase).execute(eq(request.user()), eq(request.senha()));
    }
}
