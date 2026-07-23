package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;

import com.usuario.quero_ler.infrastructure.dto.livro.*;
import com.usuario.quero_ler.core.enums.LivroIdioma;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.enums.TiposDeBusca;
import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.entities.Livro;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class LivroFixture {
    private static final Long ID = 1L;
    private static final String TITULO = "Clean Code";
    private static final String ISBN = "9780132350884";
    private static final String EDITORA = "Prentice Hall";
    private static final Year ANODEPUBLICACAO = Year.of(2008);
    private static final Integer NUMERODEPAGINAS = 464;
    private static final LivroIdioma IDIOMA = LivroIdioma.PORTUGUES;
    private static final String SINOPSE = "Um guia sobre boas práticas de programação e escrita de código limpo.";
    private static final byte[] CAPADOLIVRO = carregarImagem();
    private static final LocalDateTime DATA_DE_CADASTRO = LocalDateTime.now();

    public static BuscaDeLivrosRequest buscaDeLivrosRequest(TiposDeBusca tiposDeBusca) {
        return switch (tiposDeBusca) {
            case ISBN -> new BuscaDeLivrosRequest(TiposDeBusca.ISBN, ISBN);
            case EDITORA -> new BuscaDeLivrosRequest(TiposDeBusca.EDITORA, EDITORA);
            case TITULO -> new BuscaDeLivrosRequest(TiposDeBusca.TITULO, TITULO);
            case AUTOR -> new BuscaDeLivrosRequest(TiposDeBusca.AUTOR, AutorFixture.entity().nome());
        };
    }

    public static LivroRequest request() {
        return new LivroRequest(
                TITULO, ISBN, EDITORA, ANODEPUBLICACAO, NUMERODEPAGINAS, IDIOMA, SINOPSE,
                List.of(AutorFixture.request()));
    }

    public static Livro entity() {
        List<Autor> autores = new ArrayList<>();
        autores.add(AutorFixture.entity());
        return livro()
                .id(ID).titulo(TITULO).isbn(ISBN).editora(EDITORA)
                .anoDePublicacao(ANODEPUBLICACAO).numeroDePaginas(NUMERODEPAGINAS)
                .idioma(IDIOMA).sinopse(SINOPSE).autores(autores)
                .dataDeCadastro(LocalDateTime.now()).build();
    }

    public static Livro entityComCapa() {
        List<Autor> autores = new ArrayList<>();
        autores.add(AutorFixture.entity());
        return livro()
                .id(ID).titulo(TITULO).isbn(ISBN).editora(EDITORA)
                .anoDePublicacao(ANODEPUBLICACAO).numeroDePaginas(NUMERODEPAGINAS)
                .idioma(IDIOMA).sinopse(SINOPSE).capaDoLivro(CAPADOLIVRO).autores(autores)
                .dataDeCadastro(LocalDateTime.now()).build();
    }

    public static LivroResponse response() {
        return new LivroResponse(
                ID, TITULO, ISBN, EDITORA, ANODEPUBLICACAO, NUMERODEPAGINAS, IDIOMA, SINOPSE,
                "/livros/" + ID + "/capa", DATA_DE_CADASTRO, List.of(AutorFixture.response()));
    }

    public static LivroCardResponse responseCard() {
        return new LivroCardResponse(
                "/livros/" + ID + "/capa", TITULO, EDITORA, ANODEPUBLICACAO, NUMERODEPAGINAS,
                DATA_DE_CADASTRO, List.of(AutorFixture.response()));
    }

    public static LivroTelaLeituraResponse responseTelaDeLeitura(LeituraStatus status) {
        return new LivroTelaLeituraResponse(TITULO, status, "/livros/" + ID + "/capa", DATA_DE_CADASTRO);
    }

    public static LivroDetalhadoResponse responseDetalhado(LeituraStatus status) {
        return new LivroDetalhadoResponse(
                "/livros/" + ID + "/capa", TITULO, EDITORA, ANODEPUBLICACAO, NUMERODEPAGINAS,
                IDIOMA.name(), ISBN, SINOPSE, DATA_DE_CADASTRO, List.of(AutorFixture.response()));
    }

    private static byte[] carregarImagem() {
        try (InputStream is = LivroFixture.class.getClassLoader().getResourceAsStream("capa.jpg")) {
            if (is == null)
                return new byte[0];
            return is.readAllBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
