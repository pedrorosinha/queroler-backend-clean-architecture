package com.usuario.quero_ler.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    boolean existsByCpf(String cpf);

    Optional<UsuarioEntity> findByUserUserIgnoreCase(String login);
}