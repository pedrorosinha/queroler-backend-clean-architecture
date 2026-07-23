package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.infrastructure.dto.usuario.UsuarioAtualizadoLeitorRequest;
import com.usuario.quero_ler.infrastructure.dto.usuario.UsuarioRequestDto;
import com.usuario.quero_ler.infrastructure.dto.usuario.UsuarioResponseDto;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.fixtures.UserFixture;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapper mapper;

    @Test
    @DisplayName("Deve transformar um requestDto em domain")
    void toDomain() {
        UsuarioRequestDto dto = UserFixture.requestDto();

        Usuario resposta = mapper.toDomain(dto);

        assertNull(resposta.id());
        assertEquals(dto.nome(), resposta.nome());
        assertEquals(dto.cpf(), resposta.cpf());
        assertEquals(dto.dataDeNascimento(), resposta.dataDeNascimento());
        assertEquals(dto.email(), resposta.email());
    }

    @Test
    @DisplayName("Deve converter um usuario em response.")
    void toResponse() {
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);
        Usuario usuario = UserFixture.entidadeCompleta(user);

        UsuarioResponseDto resposta = mapper.toResponse(usuario);

        assertEquals(usuario.id(), resposta.id());
        assertEquals(usuario.nome(), resposta.nome());
        assertEquals(usuario.email(), resposta.email());
        assertEquals(usuario.dataDeNascimento(), resposta.dataDeNascimento());
        assertEquals(usuario.cidade(), resposta.cidade());
        assertEquals(usuario.estado(), resposta.estado());
        assertEquals(usuario.pais(), resposta.pais());
    }

    @Test
    @DisplayName("Deve atualizar um usuario leitor")
    void toUpdate() {
        User user = UserFixture.userEntity(UsuarioProfile.LEITOR);
        Usuario usuario = UserFixture.entidadeCompleta(user);
        UsuarioAtualizadoLeitorRequest atualizacoes = new UsuarioAtualizadoLeitorRequest(
                "nome atualizado", "email atualizado", null, "cabralha",
                "Bahia", null);

        Usuario resposta = mapper.update(usuario, atualizacoes);

        assertEquals(usuario.id(), resposta.id());
        assertEquals(atualizacoes.nome(), resposta.nome());
        assertEquals(atualizacoes.email(), resposta.email());
        assertEquals(usuario.dataDeNascimento(), resposta.dataDeNascimento());
        assertEquals(atualizacoes.cidade(), resposta.cidade());
        assertEquals(atualizacoes.estado(), resposta.estado());
    }
}
