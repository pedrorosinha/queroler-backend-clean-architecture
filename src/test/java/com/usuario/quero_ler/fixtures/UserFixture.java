package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;

import com.usuario.quero_ler.infrastructure.dto.usuario.*;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class UserFixture {
    private static final Long ID = 1L;
    private static final String NOME = "Nome SobreNome";
    private static final String EMAIL = "nome@gmail.com";
    private static final String SENHA = "Teste123&";
    private static final String CPF = "49618203000";
    private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2000, 12, 5);

    private static final Boolean CHECK_TERMO = true;
    private static final String CIDADE = "Valinhos";
    private static final String ESTADO = "São paulo";
    private static final String PAIS = "Brasil";
    private static final byte[] FOTO = carregarImagem();

    public static UsuarioRequestDto requestDto() {
        return new UsuarioRequestDto(
                NOME, EMAIL, SENHA, CPF, DATA_DE_NASCIMENTO, CHECK_TERMO);
    }

    public static UsuarioRequestDto requestDto(String senha) {
        return new UsuarioRequestDto(
                NOME, EMAIL, senha, CPF, DATA_DE_NASCIMENTO, CHECK_TERMO);
    }

    public static UsuarioDadosComplementarRequest requestDadosComplementares() {
        return new UsuarioDadosComplementarRequest(
                CIDADE, ESTADO, PAIS);
    }

    public static User userEntity(UsuarioProfile profile) {
        String senhaHash = BCrypt.hashpw(SENHA, BCrypt.gensalt());
        boolean senhaTrocada = profile.equals(UsuarioProfile.LEITOR);

        return user()
                .id(2L)
                .user(EMAIL)
                .senha(senhaHash)
                .senhaTrocada(senhaTrocada)
                .profile(profile)
                .build();
    }

    public static Usuario entidadePrincipal(User user) {
        return usuario()
                .id(ID)
                .nome(NOME)
                .email(EMAIL)
                .cpf(CPF)
                .dataDeNascimento(DATA_DE_NASCIMENTO)
                .aceitarTermos(CHECK_TERMO)
                .user(user)
                .build();
    }

    public static Usuario entidadeCompleta() {
        User user = userEntity(UsuarioProfile.ADMINISTRADOR);
        return entidadeCompleta(user);
    }

    public static Usuario entidadeCompleta(User user) {
        Usuario usuario = entidadePrincipal(user);
        return usuario
                .withCidade(CIDADE)
                .withEstado(ESTADO)
                .withPais(PAIS)
                .withFoto(FOTO);
    }

    public static UsuarioResponseDto response(Usuario user) {
        return new UsuarioResponseDto(
                user.id(), user.nome(), user.email(), user.cpf(),
                user.user() != null ? user.user().profile() : null, user.dataDeNascimento(), user.aceitarTermos(),
                user.cidade(), user.estado(), user.pais(), "/usuarios/foto");
    }

    private static byte[] carregarImagem() {
        try (InputStream is = UserFixture.class
                .getClassLoader()
                .getResourceAsStream("usuario.jpg")) {
            if (is == null) {
                return new byte[0];
            }
            return is.readAllBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
