package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, Long> {
    void deleteByDataDeCriacaoBefore(LocalDateTime dataDeCorte);
}