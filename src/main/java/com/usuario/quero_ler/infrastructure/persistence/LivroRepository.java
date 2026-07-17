package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long>, JpaSpecificationExecutor<LivroEntity> {
    Optional<LivroEntity> findByIsbn(String isbn);
}