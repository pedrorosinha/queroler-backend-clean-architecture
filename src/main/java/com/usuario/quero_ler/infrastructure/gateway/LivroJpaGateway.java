package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LivroGateway;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.mapper.LivroMapper;
import com.usuario.quero_ler.infrastructure.persistence.LivroEntity;
import com.usuario.quero_ler.infrastructure.persistence.LivroRepository;
import com.usuario.quero_ler.infrastructure.persistence.LivroSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroJpaGateway implements LivroGateway {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn).map(livroMapper::toDomain);
    }

    @Override
    public Optional<Livro> findById(Long id) {
        return livroRepository.findById(id).map(livroMapper::toDomain);
    }

    @Override
    public Livro save(Livro livro) {
        var entity = livroMapper.toPersistence(livro);
        var saved = livroRepository.save(entity);
        return livroMapper.toDomain(saved);
    }

    @Override
    public PaginatedResult<Livro> findAll(Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        var page = livroRepository.findAll(pageable).map(livroMapper::toDomain);
        return new PaginatedResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }

    @Override
    public PaginatedResult<Livro> buscarComFiltros(String titulo, String editora, String autor, Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        Specification<LivroEntity> spec = LivroSpecifications.filtro(titulo, editora, autor);
        var page = livroRepository.findAll(spec, pageable).map(livroMapper::toDomain);
        return new PaginatedResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }

    @Override
    public PaginatedResult<Livro> listarPopulares(Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        var page = livroRepository.findAll(pageable).map(livroMapper::toDomain);
        return new PaginatedResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }

    @Override
    public byte[] buscarCapa(Long livroId) {
        LivroEntity entity = livroRepository.findById(livroId)
                .orElseThrow(() -> new com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException("Livro não encontrado"));
        if (entity.getCapaDoLivro() == null) {
            throw new com.usuario.quero_ler.core.exceptions.CapaNaoCadastradaException("Capa não cadastrada");
        }
        return entity.getCapaDoLivro();
    }

    @Override
    public void inserirCapa(Long livroId, byte[] capa) {
        LivroEntity entity = livroRepository.findById(livroId)
                .orElseThrow(() -> new com.usuario.quero_ler.core.exceptions.LivroNaoEncontradoException("Livro não encontrado"));
        entity.setCapaDoLivro(capa);
        livroRepository.save(entity);
    }
}
