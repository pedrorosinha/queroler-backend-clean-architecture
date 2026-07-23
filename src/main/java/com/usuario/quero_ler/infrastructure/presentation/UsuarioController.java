package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.usecases.usuario.*;
import com.usuario.quero_ler.core.usecases.user.CriarUserUseCase;
import com.usuario.quero_ler.core.usecases.user.AlterarSenhaUseCase;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorUsuarioUseCase;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoResponseDto;
import com.usuario.quero_ler.infrastructure.dto.usuario.*;
import com.usuario.quero_ler.infrastructure.mapper.UsuarioMapper;
import com.usuario.quero_ler.core.exceptions.AusenciaDeDadosException;
import com.usuario.quero_ler.core.exceptions.CapaForaDePadraoException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUserUseCase criarUserUseCase;
    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarFotoUsuarioUseCase buscarFotoUsuarioUseCase;
    private final AtualizarDadosAdicionaisUseCase atualizarDadosAdicionaisUseCase;
    private final AtualizarDadosLeitorUseCase atualizarDadosLeitorUseCase;
    private final AtualizarDadosAdministradorUseCase atualizarDadosAdministradorUseCase;
    private final AlterarSenhaUseCase alterarSenhaUseCase;
    private final ExcluirPerfilUseCase excluirPerfilUseCase;
    private final ListarAcompanhamentoPorUsuarioUseCase listarAcompanhamentoPorUsuarioUseCase;
    private final UsuarioMapper usuarioMapper;
    private final tools.jackson.databind.ObjectMapper objectMapper;
    private final Validator validator;
    private final UsuarioAtualHelper usuarioAtual;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioResponseDto> criar(
            @RequestPart(value = "imagem", required = false) MultipartFile foto,
            @RequestPart("dados") String dadosJson) throws Exception {

        UsuarioRequestDto dto = objectMapper.readValue(dadosJson, UsuarioRequestDto.class);
        var violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        User user = criarUserUseCase.execute(dto.email(), dto.senha(), UsuarioProfile.LEITOR);

        Usuario usuario = usuarioMapper.toDomain(dto);
        usuario = usuario.withUser(user);

        if (foto != null && !foto.isEmpty()) {
            validarFoto(foto);
            usuario = usuario.withFoto(foto.getBytes());
        }

        usuario = criarUsuarioUseCase.execute(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioMapper.toResponse(usuario));
    }

    @GetMapping
    public ResponseEntity<UsuarioResponseDto> dadosDoUsuario() {
        Usuario usuario = usuarioAtual.getUsuarioAtual();
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }

    @PutMapping(value = "/dados-adicionais", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> inserirDadosAdicionais(
            @RequestPart(value = "dados", required = false) String dados,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) throws Exception {

        if (dados == null && (imagem == null || imagem.isEmpty())) {
            throw new AusenciaDeDadosException("É necessário enviar dados ou imagem.");
        }

        UsuarioDadosComplementarRequest dto = null;
        byte[] fotoBytes = null;

        if (dados != null) {
            dto = objectMapper.readValue(dados, UsuarioDadosComplementarRequest.class);
        }
        if (imagem != null && !imagem.isEmpty()) {
            validarFoto(imagem);
            fotoBytes = imagem.getBytes();
        }

        String cidade = dto != null ? dto.cidade() : null;
        String estado = dto != null ? dto.estado() : null;
        String pais = dto != null ? dto.pais() : null;

        atualizarDadosAdicionaisUseCase.execute(
                usuarioAtual.getUsuarioAtual(), cidade, estado, pais, fotoBytes);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody @Valid UsuarioAlterarSenhaRequest dto) {
        alterarSenhaUseCase.execute(
                usuarioAtual.getUsuarioLogado(), dto.senhaAtual(), dto.senhaNova());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> alterar(
            @RequestPart(value = "dados", required = false) String dados,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) throws Exception {

        if (dados == null && (imagem == null || imagem.isEmpty())) {
            throw new AusenciaDeDadosException("É necessário enviar dados ou imagem.");
        }

        UsuarioAtualizadoLeitorRequest dto = null;
        byte[] fotoBytes = null;

        if (dados != null) {
            dto = objectMapper.readValue(dados, UsuarioAtualizadoLeitorRequest.class);
        }
        if (imagem != null && !imagem.isEmpty()) {
            validarFoto(imagem);
            fotoBytes = imagem.getBytes();
        }

        Usuario usuario = usuarioAtual.getUsuarioAtual();
        if (dto != null) {
            usuario = usuarioMapper.update(usuario, dto);
        }

        String email = dto != null && dto.email() != null ? dto.email() : usuario.email();
        String nome = dto != null ? dto.nome() : null;
        var dataNasc = dto != null ? dto.dataDeNascimento() : null;
        String cidade = dto != null ? dto.cidade() : null;
        String estado = dto != null ? dto.estado() : null;
        String pais = dto != null ? dto.pais() : null;

        atualizarDadosLeitorUseCase.execute(usuario, email, nome, dataNasc, cidade, estado, pais, fotoBytes);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/administrador", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> alterarAdministrador(
            @RequestPart(value = "dados", required = false) String dados,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) throws Exception {

        if (dados == null && (imagem == null || imagem.isEmpty())) {
            throw new AusenciaDeDadosException("É necessário enviar dados ou imagem.");
        }

        UsuarioAtualizadoAdministradorRequest dto = null;
        byte[] fotoBytes = null;

        if (dados != null) {
            dto = objectMapper.readValue(dados, UsuarioAtualizadoAdministradorRequest.class);
        }
        if (imagem != null && !imagem.isEmpty()) {
            validarFoto(imagem);
            fotoBytes = imagem.getBytes();
        }

        Usuario usuario = usuarioAtual.getUsuarioAtual();
        if (dto != null) {
            usuario = usuarioMapper.update(usuario, dto);
        }

        var dataNasc = dto != null ? dto.dataDeNascimento() : null;
        String cidade = dto != null ? dto.cidade() : null;
        String estado = dto != null ? dto.estado() : null;
        String pais = dto != null ? dto.pais() : null;

        atualizarDadosAdministradorUseCase.execute(usuario, dataNasc, cidade, estado, pais, fotoBytes);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirPerfil() {
        excluirPerfilUseCase.execute(usuarioAtual.getUsuarioAtual(), usuarioAtual.getUsuarioLogado().profile());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/foto")
    public ResponseEntity<byte[]> buscarFoto() {
        byte[] foto = buscarFotoUsuarioUseCase.execute(usuarioAtual.getUsuarioAtualId());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(foto);
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<AcompanhamentoResponseDto>> listarComentariosPorUsuario(@PathVariable Long id) {
        var acompanhamentos = listarAcompanhamentoPorUsuarioUseCase.execute(id);
        var response = acompanhamentos.stream()
                .map(a -> new AcompanhamentoResponseDto(
                        a.id(), a.paginaInicial(), a.paginaFinal(), a.comentario(),
                        a.diarioDeLeitura() != null ? a.diarioDeLeitura().id() : null,
                        a.diarioDeLeitura() != null && a.diarioDeLeitura().leitura() != null
                                && a.diarioDeLeitura().leitura().usuario() != null
                                ? a.diarioDeLeitura().leitura().usuario().id() : null))
                .toList();
        return ResponseEntity.ok(response);
    }

    private void validarFoto(MultipartFile foto) {
        try {
            if (foto == null || foto.isEmpty()) {
                return;
            }

            long tamanhoMaximo = 10 * 1024 * 1024;
            if (foto.getSize() > tamanhoMaximo) {
                throw new CapaForaDePadraoException("Imagem excede o tamanho máximo de 10MB");
            }

            List<String> tiposPermitidos = List.of(
                    "image/jpeg",
                    "image/jpg",
                    "image/png");

            if (foto.getContentType() == null ||
                    !tiposPermitidos.contains(foto.getContentType())) {
                throw new CapaForaDePadraoException("Formato inválido. Use JPG ou PNG");
            }

            BufferedImage imagem = ImageIO.read(foto.getInputStream());
            if (imagem == null) {
                throw new CapaForaDePadraoException("Arquivo enviado não é uma imagem válida");
            }

        } catch (IOException e) {
            throw new CapaForaDePadraoException("Erro ao processar imagem");
        }
    }
}
