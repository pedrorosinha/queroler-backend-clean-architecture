package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.LoginResult;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.exceptions.CredenciaisInvalidasException;
import com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.infrastructure.security.BCryptPasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUseCase")
class LoginUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private BCryptPasswordHasher hasher;
    private LoginUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        hasher = new BCryptPasswordHasher();
        useCase = new LoginUseCaseImpl(userGateway, hasher);
    }

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar LoginResult")
    void loginSucesso() {
        String hash = hasher.hash("Senha@123");
        User user = user().id(1L).user("test@email.com").senha(hash)
                .profile(UsuarioProfile.LEITOR).senhaTrocada(true).build();
        when(userGateway.findByUserIgnoreCase("test@email.com")).thenReturn(Optional.of(user));

        LoginResult resultado = useCase.execute("test@email.com", "Senha@123");

        assertNotNull(resultado);
        assertEquals(1L, resultado.user().id());
        assertEquals(UsuarioProfile.LEITOR, resultado.user().profile());
        assertFalse(resultado.primeiroLogin());
    }

    @Test
    @DisplayName("Deve retornar primeiroLogin=true quando ADMINISTRADOR e senhaTrocada=false")
    void primeiroLoginAdmin() {
        String hash = hasher.hash("Senha@123");
        User user = user().id(1L).user("test@email.com").senha(hash)
                .profile(UsuarioProfile.ADMINISTRADOR).senhaTrocada(false).build();
        when(userGateway.findByUserIgnoreCase("test@email.com")).thenReturn(Optional.of(user));

        LoginResult resultado = useCase.execute("test@email.com", "Senha@123");

        assertTrue(resultado.primeiroLogin());
    }

    @Test
    @DisplayName("Deve lancar excecao quando usuario nao encontrado")
    void usuarioNaoEncontrado() {
        when(userGateway.findByUserIgnoreCase("inexistente@email.com")).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> useCase.execute("inexistente@email.com", "Senha@123"));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha esta incorreta")
    void senhaIncorreta() {
        String hash = hasher.hash("Senha@123");
        User user = user().id(1L).user("test@email.com").senha(hash).build();
        when(userGateway.findByUserIgnoreCase("test@email.com")).thenReturn(Optional.of(user));

        assertThrows(CredenciaisInvalidasException.class,
                () -> useCase.execute("test@email.com", "Errada@999"));
    }
}
