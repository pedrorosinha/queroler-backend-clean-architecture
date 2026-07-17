package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeituraRepository extends JpaRepository<LeituraEntity, Long> {
    Optional<LeituraEntity> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId);

    Optional<LeituraEntity> findByLivro_IdAndUsuario_Id(Long livroid, Long usuarioId);

    boolean existsByUsuarioIdAndLivroId(Long usuarioId, Long livroId);

    Page<LeituraEntity> findAllByUsuarioId(Long usuarioId, Pageable pageable);

    @Query("SELECT ul.livro FROM LeituraEntity ul WHERE ul.usuario.id = :usuarioId")
    Page<LivroEntity> findLivrosByUsuarioId(Long usuarioId, Pageable pageable);
}
