package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.User;

import java.util.Optional;

public interface UserGateway {
    Optional<User> findByUserIgnoreCase(String user);

    User save(User user);
}
