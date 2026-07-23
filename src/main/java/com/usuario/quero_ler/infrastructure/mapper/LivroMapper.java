package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.infrastructure.persistence.LivroEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LivroMapper {

    private final AutorMapper autorMapper;

    LivroMapper(AutorMapper autorMapper) {
        this.autorMapper = autorMapper;
    }

    public Livro toDomain(LivroEntity entity) {
        return new Livro(entity.getId(), entity.getTitulo(), entity.getIsbn(),
                entity.getEditora(), entity.getAnoDePublicacao(), entity.getNumeroDePaginas(),
                entity.getIdioma(), entity.getSinopse(), entity.getCapaDoLivro(),
                entity.getAutores() != null
                        ? entity.getAutores().stream().map(autorMapper::toDomain).toList()
                        : new ArrayList<>(),
                null, entity.getDataDeCadastro(), entity.getQuantidadeDeUso());
    }

    public LivroEntity toPersistence(Livro domain) {
        return LivroEntity.builder()
                .id(domain.id())
                .titulo(domain.titulo())
                .isbn(domain.isbn())
                .editora(domain.editora())
                .anoDePublicacao(domain.anoDePublicacao())
                .numeroDePaginas(domain.numeroDePaginas())
                .idioma(domain.idioma())
                .sinopse(domain.sinopse())
                .capaDoLivro(domain.capaDoLivro())
                .autores(domain.autores() != null
                        ? domain.autores().stream().map(autorMapper::toPersistence).toList()
                        : new ArrayList<>())
                .build();
    }
}
