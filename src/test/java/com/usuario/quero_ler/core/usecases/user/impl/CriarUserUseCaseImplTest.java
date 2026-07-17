package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.exceptions.SenhaInvalidaException;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.core.utils.PasswordHasher;
import com.usuario.quero_ler.infrastructure.security.BCryptPasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarUserUseCase")
class CriarUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private CriarUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CriarUserUseCaseImpl(userGateway, new BCryptPasswordHasher());
    }

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    @DisplayName("Deve criar usuario LEITOR com senhaTrocada=true")
    void criarLeitor() {
        when(userGateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User resultado = useCase.execute("test@email.com", "Senha@123", UsuarioProfile.LEITOR);

        verify(userGateway).save(userCaptor.capture());
        User salvo = userCaptor.getValue();
        assertEquals("test@email.com", salvo.user());
        assertTrue(salvo.senhaTrocada());
        assertEquals(UsuarioProfile.LEITOR, salvo.profile());
        assertNotNull(salvo.senha());
        assertNotEquals("Senha@123", salvo.senha());
    }

    @Test
    @DisplayName("Deve criar usuario ADMINISTRADOR com senhaTrocada=false")
    void criarAdmin() {
        when(userGateway.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User resultado = useCase.execute("admin@email.com", "Senha@123", UsuarioProfile.ADMINISTRADOR);

        verify(userGateway).save(userCaptor.capture());
        assertFalse(userCaptor.getValue().senhaTrocada());
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha e nula")
    void senhaNula() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", null, UsuarioProfile.LEITOR));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha e vazia")
    void senhaVazia() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", "", UsuarioProfile.LEITOR));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha e muito curta")
    void senhaCurta() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", "A1@bc", UsuarioProfile.LEITOR));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha nao tem maiuscula")
    void semMaiuscula() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", "senha@123", UsuarioProfile.LEITOR));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha nao tem numero")
    void semNumero() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", "Senha@abc", UsuarioProfile.LEITOR));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha nao tem caractere especial")
    void semEspecial() {
        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute("test@email.com", "Senha123", UsuarioProfile.LEITOR));
    }
}
