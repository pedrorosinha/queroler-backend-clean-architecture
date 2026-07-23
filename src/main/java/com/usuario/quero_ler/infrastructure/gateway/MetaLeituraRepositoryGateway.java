package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.MetaLeituraGateway;
import com.usuario.quero_ler.infrastructure.mapper.MetaLeituraMapper;
import com.usuario.quero_ler.infrastructure.mapper.UsuarioMapper;
import com.usuario.quero_ler.infrastructure.persistence.MetaLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetaLeituraRepositoryGateway implements MetaLeituraGateway {

    private final MetaLeituraRepository metaLeituraRepository;
    private final MetaLeituraMapper metaLeituraMapper;
    private final UsuarioMapper usuarioMapper;

    @Override
    public MetaLeitura save(MetaLeitura meta) {
        var entity = metaLeituraMapper.toPersistence(meta);
        var saved = metaLeituraRepository.save(entity);
        return metaLeituraMapper.toDomain(saved);
    }

    @Override
    public void deleteAllByUsuario(Usuario usuario) {
        var usuarioEntity = usuarioMapper.toPersistence(usuario);
        metaLeituraRepository.deleteAllByUsuario(usuarioEntity);
    }

    @Override
    public boolean existsByUsuarioAndAno(Usuario usuario, Integer ano) {
        var usuarioEntity = usuarioMapper.toPersistence(usuario);
        return metaLeituraRepository.existsByUsuarioAndAno(usuarioEntity, ano);
    }
}
