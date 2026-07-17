package com.usuario.quero_ler.core.usecases.user.impl;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.exceptions.CredenciaisInvalidasException;
import com.usuario.quero_ler.core.exceptions.SenhaInvalidaException;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.infrastructure.security.BCryptPasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AlterarSenhaUseCase")
class AlterarSenhaUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private BCryptPasswordHasher hasher;
    private AlterarSenhaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        hasher = new BCryptPasswordHasher();
        useCase = new AlterarSenhaUseCaseImpl(userGateway, hasher);
    }

    private User usuarioComSenha(String hash, UsuarioProfile profile, boolean senhaTrocada) {
        return user()
                .id(1L)
                .user("test@email.com")
                .senha(hash)
                .profile(profile)
                .senhaTrocada(senhaTrocada)
                .build();
    }

    @Test
    @DisplayName("Deve alterar senha com sucesso")
    void alterarSenha() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.LEITOR, true);

        useCase.execute(user, "Senha@123", "Nova@456");

        verify(userGateway).save(argThat(u -> u.senha() != null && !u.senha().equals(hashAtual)));
    }

    @Test
    @DisplayName("Deve lancar excecao quando senha atual nao confere")
    void senhaAtualIncorreta() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.LEITOR, true);

        assertThrows(CredenciaisInvalidasException.class,
                () -> useCase.execute(user, "Errada@999", "Nova@456"));
        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar excecao quando nova senha e invalida")
    void novaSenhaInvalida() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.LEITOR, true);

        assertThrows(SenhaInvalidaException.class,
                () -> useCase.execute(user, "Senha@123", "fraca"));
        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve marcar senhaTrocada=true para ADMINISTRADOR na primeira troca")
    void adminPrimeiraTroca() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.ADMINISTRADOR, false);

        useCase.execute(user, "Senha@123", "Nova@456");

        verify(userGateway).save(argThat(u -> u.senhaTrocada()));
    }

    @Test
    @DisplayName("Deve marcar senhaTrocada=true para MODERADOR na primeira troca")
    void moderadorPrimeiraTroca() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.MODERADOR, false);

        useCase.execute(user, "Senha@123", "Nova@456");

        verify(userGateway).save(argThat(u -> u.senhaTrocada()));
    }

    @Test
    @DisplayName("Deve manter senhaTrocada como ja era true para ADMINISTRADOR")
    void adminJaTrocada() {
        String hashAtual = hasher.hash("Senha@123");
        User user = usuarioComSenha(hashAtual, UsuarioProfile.ADMINISTRADOR, true);

        useCase.execute(user, "Senha@123", "Nova@456");

        verify(userGateway).save(argThat(User::senhaTrocada));
    }
}
