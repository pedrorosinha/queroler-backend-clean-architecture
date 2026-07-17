package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.infrastructure.mapper.UsuarioMapper;
import com.usuario.quero_ler.infrastructure.persistence.UserEntity;
import com.usuario.quero_ler.infrastructure.persistence.UsuarioRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryGateway implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EntityManager entityManager;

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return usuarioRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email).map(usuarioMapper::toDomain);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Override
    public Usuario save(Usuario usuario) {
        var entity = usuarioMapper.toPersistence(usuario);
        if (usuario.user() != null && usuario.user().id() != null) {
            entity.setUser(entityManager.getReference(UserEntity.class, usuario.user().id()));
        }
        var saved = usuarioRepository.save(entity);
        return usuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByUserLoginIgnoreCase(String login) {
        return usuarioRepository.findByUserUserIgnoreCase(login).map(usuarioMapper::toDomain);
    }

    @Override
    public void delete(Usuario usuario) {
        var entity = usuarioMapper.toPersistence(usuario);
        usuarioRepository.delete(entity);
    }
}
