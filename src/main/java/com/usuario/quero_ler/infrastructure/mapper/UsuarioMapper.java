package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.infrastructure.dto.usuario.*;
import com.usuario.quero_ler.infrastructure.persistence.UsuarioEntity;
import com.usuario.quero_ler.core.utils.Cpf;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final EntityMapper entityMapper;

    UsuarioMapper(@Lazy EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    public Usuario toDomain(UsuarioRequestDto dto) {
        Cpf.validateOrThrow(dto.cpf());
        return new Usuario(null, dto.nome(), dto.email(), Cpf.normalize(dto.cpf()),
                dto.dataDeNascimento(), dto.checkTermo(),
                null, null, null, null, null, null, null, null);
    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(entity.getId(), entity.getNome(), entity.getEmail(), entity.getCpf(),
                entity.getDataDeNascimento(), entity.getAceitarTermos(),
                entity.getCidade(), entity.getEstado(), entity.getPais(), entity.getFoto(),
                null, null, null, null);
    }

    public UsuarioEntity toPersistence(Usuario domain) {
        if (domain == null) return null;
        return UsuarioEntity.builder()
                .id(domain.id())
                .nome(domain.nome())
                .email(domain.email())
                .cpf(domain.cpf())
                .dataDeNascimento(domain.dataDeNascimento())
                .aceitarTermos(domain.aceitarTermos())
                .cidade(domain.cidade())
                .estado(domain.estado())
                .pais(domain.pais())
                .foto(domain.foto())
                .build();
    }

    public Usuario complementarCadastro(Usuario domain, UsuarioDadosComplementarRequest dto) {
        return domain
                .withCidade(dto.cidade())
                .withEstado(dto.estado())
                .withPais(dto.pais());
    }

    public Usuario update(Usuario domain, UsuarioAtualizadoLeitorRequest dto) {
        return domain
                .withNome(dto.nome() != null ? dto.nome() : domain.nome())
                .withEmail(dto.email() != null ? dto.email() : domain.email())
                .withDataDeNascimento(dto.dataDeNascimento() != null ? dto.dataDeNascimento() : domain.dataDeNascimento())
                .withCidade(dto.cidade() != null ? dto.cidade() : domain.cidade())
                .withEstado(dto.estado() != null ? dto.estado() : domain.estado())
                .withPais(dto.pais() != null ? dto.pais() : domain.pais());
    }

    public Usuario update(Usuario domain, UsuarioAtualizadoAdministradorRequest dto) {
        Usuario result = domain;
        if (dto.dataDeNascimento() != null) {
            result = result.withDataDeNascimento(dto.dataDeNascimento());
        }
        if (dto.cidade() != null) {
            result = result.withCidade(dto.cidade());
        }
        if (dto.estado() != null) {
            result = result.withEstado(dto.estado());
        }
        if (dto.pais() != null) {
            result = result.withPais(dto.pais());
        }
        return result;
    }

    public UsuarioResponseDto toResponse(Usuario domain) {
        return new UsuarioResponseDto(
                domain.id(),
                domain.nome(),
                domain.email(),
                domain.cpf(),
                domain.user() != null ? domain.user().profile() : null,
                domain.dataDeNascimento(),
                domain.aceitarTermos(),
                domain.cidade(),
                domain.estado(),
                domain.pais(),
                getUrlFoto(domain)
        );
    }

    private String getUrlFoto(Usuario domain) {
        if (domain.foto() != null) {
            return "/usuarios/foto";
        }
        return "Foto não encontrada.";
    }
}
