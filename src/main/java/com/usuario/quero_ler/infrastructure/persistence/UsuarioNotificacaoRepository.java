package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsuarioNotificacaoRepository extends JpaRepository<UsuarioNotificacaoEntity, Long> {
    @Modifying
    @Query(value = """
                INSERT INTO tb_usuario_notificacao (usuario_id, notificacao_id, visualizada)
                SELECT u.id, :notificacaoId, false
                FROM tb_usuario u
            """, nativeQuery = true)
    void enviarParaTodosUsuarios(Long notificacaoId);

    @Modifying
    @Query("""
            UPDATE UsuarioNotificacaoEntity un
            SET un.visualizada = true,
                un.dataLeitura = CURRENT_TIMESTAMP
            WHERE un.usuario.id = :usuarioId
            AND un.visualizada = false
            """)
    void marcarComoLidas(@Param("usuarioId") Long usuarioId);

    void deleteByNotificacaoDataDeCriacaoBefore(LocalDateTime data);

    List<UsuarioNotificacaoEntity> findByUsuarioId(Long usuarioId);

    @Query("""
            SELECT un FROM UsuarioNotificacaoEntity un
            JOIN FETCH un.notificacao n
            WHERE un.usuario.id = :usuarioId
            ORDER BY n.dataDeCriacao DESC
            """)
    Page<UsuarioNotificacaoEntity> buscarTodasPorUsuario(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
