package com.usuario.quero_ler.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.*;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LivroSpecifications {

    public static Specification<LivroEntity> filtro(String titulo, String editora, String autor) {
        return (Root<LivroEntity> root, CriteriaQuery<?> criterio, CriteriaBuilder builder) -> {
            Predicate predicate = builder.conjunction();

            if (titulo != null && !titulo.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder
                        .lower(root.get("titulo")), "%" + titulo.toLowerCase(Locale.ROOT) + "%"));
            }

            if (editora != null && !editora.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder
                        .lower(root.get("editora")), "%" + editora.toLowerCase(Locale.ROOT) + "%"));
            }

            if (autor != null && !autor.isEmpty()) {
                Join<LivroEntity, AutorEntity> autoresJoin = root.join("autores", JoinType.LEFT);
                criterio.distinct(true);
                predicate = builder.and(predicate,
                        builder.like(
                                builder.lower(autoresJoin.get("nome")),
                                "%" + autor.toLowerCase(Locale.ROOT) + "%"
                        )
                );
            }
            return predicate;
        };
    }

    public static Pageable top5MaisVotados() {
        return org.springframework.data.domain.PageRequest.of(
                0,
                5,
                org.springframework.data.domain.Sort.by("quantidadeDeUso").descending()
                        .and(org.springframework.data.domain.Sort.by("dataDeCadastro").descending())
        );
    }
}
