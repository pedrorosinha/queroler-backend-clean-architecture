package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.Usuario;

import java.util.Optional;

public interface UsuarioGateway {
    boolean existsByEmailIgnoreCase(String email);

    Optional<Usuario> findByEmailIgnoreCase(String email);

    boolean existsByCpf(String cpf);

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUserLoginIgnoreCase(String login);

    void delete(Usuario usuario);
}
