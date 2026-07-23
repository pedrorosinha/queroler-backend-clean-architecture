package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.gateway.UserGateway;
import com.usuario.quero_ler.infrastructure.mapper.EntityMapper;
import com.usuario.quero_ler.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryGateway implements UserGateway {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    @Override
    public Optional<User> findByUserIgnoreCase(String user) {
        return userRepository.findByUserIgnoreCase(user).map(entityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = entityMapper.toPersistence(user);
        var saved = userRepository.save(entity);
        return entityMapper.toDomain(saved);
    }
}
