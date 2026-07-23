package com.usuario.quero_ler.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AcompanhamentoDeLeituraRepository extends JpaRepository<AcompanhamentoDeLeituraEntity, Long> {
        List<AcompanhamentoDeLeituraEntity> findByDiarioDeLeitura_Leitura_Livro_Id(Long livroId);

        List<AcompanhamentoDeLeituraEntity> findByDiarioDeLeitura_Leitura_Usuario_Id(Long usuarioId);

        @Query("select a from AcompanhamentoDeLeituraEntity a " +
                        "join fetch a.diarioDeLeitura d " +
                        "join fetch d.leitura ul " +
                        "join fetch ul.usuario u " +
                        "where ul.livro.id = :livroId")
        List<AcompanhamentoDeLeituraEntity> findByLivroIdWithJoins(@Param("livroId") Long livroId);

        @Query("select a from AcompanhamentoDeLeituraEntity a " +
                        "join fetch a.diarioDeLeitura d " +
                        "join fetch d.leitura ul " +
                        "join fetch ul.usuario u " +
                        "where ul.usuario.id = :usuarioId")
        List<AcompanhamentoDeLeituraEntity> findByUsuarioIdWithJoins(@Param("usuarioId") Long usuarioId);
}
