package com.usuario.quero_ler.infrastructure.presentation;

import tools.jackson.databind.ObjectMapper;
import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.usecases.livro.*;
import com.usuario.quero_ler.core.usecases.acompanhamento.ListarAcompanhamentoPorLivroUseCase;
import com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.livro.LivroRequest;
import com.usuario.quero_ler.infrastructure.dto.livro.LivroResponse;
import com.usuario.quero_ler.infrastructure.dto.livro.LivroCardResponse;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final CriarLivroUseCase criarLivroUseCase;
    private final InserirCapaLivroUseCase inserirCapaLivroUseCase;
    private final BuscarLivroUseCase buscarLivroUseCase;
    private final BuscarLivroPorIsbnUseCase buscarLivroPorIsbnUseCase;
    private final BuscarCapaLivroUseCase buscarCapaLivroUseCase;
    private final BuscarLivrosComFiltrosUseCase buscarLivrosComFiltrosUseCase;
    private final ListarLivrosTelaLeituraUseCase listarLivrosTelaLeituraUseCase;
    private final ListarLivrosPopularesUseCase listarLivrosPopularesUseCase;
    private final ListarLivrosDetalhadosUseCase listarLivrosDetalhadosUseCase;
    private final ListarAcompanhamentoPorLivroUseCase listarAcompanhamentoPorLivroUseCase;
    private final UsuarioAtualHelper usuarioAtual;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> criarLivro(
            @RequestPart("dados") String dadosJson,
            @RequestPart(value = "imagem", required = false) MultipartFile capaDoLivro) throws Exception {

        LivroRequest livroRequest = objectMapper.readValue(dadosJson, LivroRequest.class);
        var violations = validator.validate(livroRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        byte[] capaBytes = null;
        if (capaDoLivro != null && !capaDoLivro.isEmpty()) {
            validarImagem(capaDoLivro);
            capaBytes = capaDoLivro.getBytes();
        }

        Livro livro = new Livro(null, livroRequest.titulo(), livroRequest.isbn(),
                livroRequest.editora(), livroRequest.anoDePublicacao(),
                livroRequest.numeroDePaginas(), livroRequest.idioma(),
                livroRequest.sinopse(), null, null, null, null, null);

        List<Autor> autores = livroRequest.autores().stream()
                .map(a -> new Autor(null, a.nome(), null))
                .toList();

        criarLivroUseCase.execute(livro, autores, capaBytes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarLivro(@PathVariable Long id) {
        Livro livro = buscarLivroUseCase.execute(id);
        return ResponseEntity.ok(toResponse(livro));
    }

    @GetMapping("/buscar/{isbn}")
    public ResponseEntity<LivroResponse> buscarPorIsbn(@PathVariable String isbn) {
        Livro livro = buscarLivroPorIsbnUseCase.execute(isbn);
        return ResponseEntity.ok(toResponse(livro));
    }

    @GetMapping("/populares")
    public ResponseEntity<Page<LivroResponse>> listarPopulares(Pageable pageable) {
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        var result = listarLivrosPopularesUseCase.execute(pagination);
        var content = result.content().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(new PageImpl<>(content, pageable, result.totalElements()));
    }

    @GetMapping("/detalhados")
    public ResponseEntity<?> listarDetalhados(Pageable pageable) {
        Long usuarioId = usuarioAtual.getUsuarioAtualId();
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        var result = listarLivrosDetalhadosUseCase.execute(usuarioId, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/capa")
    public ResponseEntity<byte[]> buscarCapaLivro(@PathVariable Long id) {
        byte[] capa = buscarCapaLivroUseCase.execute(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(capa);
    }

    @GetMapping
    public ResponseEntity<Page<LivroCardResponse>> buscarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String editora,
            @RequestParam(required = false) String autor,
            Pageable pageable) {
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        var result = buscarLivrosComFiltrosUseCase.execute(titulo, editora, autor, pagination);
        var content = result.content().stream().map(this::toCardResponse).toList();
        return ResponseEntity.ok(new PageImpl<>(content, pageable, result.totalElements()));
    }

    @GetMapping("/tela_de_leitura")
    public ResponseEntity<?> livrosDoUsuarioParaTelaDeLeitura(Pageable pageable) {
        Long usuarioId = usuarioAtual.getUsuarioAtualId();
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        var result = listarLivrosTelaLeituraUseCase.execute(usuarioId, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<AcompanhamentoResponseDto>> listarComentariosPorLivro(@PathVariable Long id) {
        var acompanhamentos = listarAcompanhamentoPorLivroUseCase.execute(id);
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

    @PutMapping(value = "/{id}/capa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> inserirCapaLivro(@PathVariable Long id,
                                                 @RequestPart MultipartFile capaDoLivro) throws IOException {
        if (capaDoLivro == null || capaDoLivro.isEmpty()) {
            throw new LivroNaoEncontradoException("O arquivo da capa não pode ser nulo.");
        }
        validarImagem(capaDoLivro);
        inserirCapaLivroUseCase.execute(id, capaDoLivro.getBytes());
        return ResponseEntity.ok().build();
    }

    private LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
                livro.id(),
                livro.titulo(),
                livro.isbn(),
                livro.editora(),
                livro.anoDePublicacao(),
                livro.numeroDePaginas(),
                livro.idioma(),
                livro.sinopse(),
                livro.id() != null ? "/livros/" + livro.id() + "/capa" : null,
                livro.dataDeCadastro(),
                livro.autores() != null
                        ? livro.autores().stream()
                            .map(a -> new com.usuario.quero_ler.infrastructure.dto.autor.AutorResponse(a.id(), a.nome()))
                            .toList()
                        : List.of()
        );
    }

    private LivroCardResponse toCardResponse(Livro livro) {
        return new LivroCardResponse(
                livro.id() != null ? "/livros/" + livro.id() + "/capa" : null,
                livro.titulo(),
                livro.editora(),
                livro.anoDePublicacao(),
                livro.numeroDePaginas(),
                livro.dataDeCadastro(),
                livro.autores() != null
                        ? livro.autores().stream()
                            .map(a -> new com.usuario.quero_ler.infrastructure.dto.autor.AutorResponse(a.id(), a.nome()))
                            .toList()
                        : List.of()
        );
    }

    private void validarImagem(MultipartFile arquivo) {
        if (arquivo.isEmpty()) {
            throw new LivroNaoEncontradoException("O arquivo não pode ser vazio.");
        }
        String contentType = arquivo.getContentType();
        List<String> tiposPermitidos = List.of(
                "image/jpeg",
                "image/jpg",
                "image/png"
        );
        if (contentType == null || !tiposPermitidos.contains(contentType)) {
            throw new LivroNaoEncontradoException("Formato inválido. Use JPG ou PNG");
        }
        try (ImageInputStream iis = ImageIO.createImageInputStream(arquivo.getInputStream())) {
            if (iis == null) {
                throw new LivroNaoEncontradoException("Não foi possível ler o arquivo.");
            }
            Iterator<javax.imageio.ImageReader> readers = ImageIO.getImageReaders(iis);
            if (!readers.hasNext()) {
                throw new LivroNaoEncontradoException("Formato de imagem não suportado.");
            }
        } catch (IOException e) {
            throw new LivroNaoEncontradoException("Erro ao validar a imagem: " + e.getMessage());
        }
        if (arquivo.getSize() > 10 * 1024 * 1024) {
            throw new LivroNaoEncontradoException("A imagem não pode ter mais que 10MB.");
        }
    }
}
