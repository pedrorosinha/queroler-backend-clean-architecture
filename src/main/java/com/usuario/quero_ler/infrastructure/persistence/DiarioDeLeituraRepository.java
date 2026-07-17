package com.usuario.quero_ler.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiarioDeLeituraRepository extends JpaRepository<DiarioDeLeituraEntity, Long> {

    boolean existsByLeitura(LeituraEntity leitura);

    @Query("SELECT d FROM DiarioDeLeituraEntity d WHERE d.leitura.usuario.id = :usuarioId AND d.leitura.livro.id = :livroId")
    Optional<DiarioDeLeituraEntity> findByUsuarioIdAndLivroId(@Param("usuarioId") Long usuarioId, @Param("livroId") Long livroId);
}
