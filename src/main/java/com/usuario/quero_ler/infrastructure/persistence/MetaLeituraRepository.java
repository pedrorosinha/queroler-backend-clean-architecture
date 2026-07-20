package com.usuario.quero_ler.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaLeituraRepository extends JpaRepository<MetaLeituraEntity, Long> {
    boolean existsByUsuarioAndAno(UsuarioEntity usuario, Integer ano);

    void deleteAllByUsuario(UsuarioEntity usuario);
}