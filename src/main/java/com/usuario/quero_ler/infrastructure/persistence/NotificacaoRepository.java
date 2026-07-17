package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, Long> {
    void deleteByDataDeCriacaoBefore(LocalDateTime dataDeCorte);
}