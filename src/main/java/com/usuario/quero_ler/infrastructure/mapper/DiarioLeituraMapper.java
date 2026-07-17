package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoLeituraResponseDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraAtualizadoRequest;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraRequestDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraResponseDto;
import com.usuario.quero_ler.infrastructure.persistence.AcompanhamentoDeLeituraEntity;
import com.usuario.quero_ler.infrastructure.persistence.DiarioDeLeituraEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DiarioLeituraMapper {

    public DiarioDeLeitura toDomain(DiarioDeLeituraEntity entity) {
        if (entity == null) return null;
        return new DiarioDeLeitura(entity.getId(), null,
                entity.getInicioDaLeitura(), entity.getTerminoDaLeitura(),
                entity.getPaginasLidas(), null, entity.getNota(),
                entity.getTituloDaResenha(), entity.getSpoiler(), entity.getResenha());
    }

    public DiarioDeLeitura toDomain(DiarioDeLeituraRequestDto dto) {
        if (dto == null) return null;
        return new DiarioDeLeitura(null, null,
                dto.inicioDaLeitura(), dto.terminoDaLeitura(), dto.paginasLidas(),
                null, dto.nota(), dto.tituloDaResenha(), dto.spoiler(), dto.resenha());
    }

    public DiarioDeLeitura toDomain(DiarioDeLeituraAtualizadoRequest dto) {
        if (dto == null) return null;
        return new DiarioDeLeitura(null, null,
                dto.inicioDaLeitura(), dto.terminoDaLeitura(), dto.paginasLidas(),
                null, dto.nota(), dto.tituloDaResenha(), null, dto.resenha());
    }

    public DiarioDeLeituraEntity toPersistence(DiarioDeLeitura domain) {
        if (domain == null) return null;
        return DiarioDeLeituraEntity.builder()
                .id(domain.id())
                .inicioDaLeitura(domain.inicioDaLeitura())
                .terminoDaLeitura(domain.terminoDaLeitura())
                .paginasLidas(domain.paginasLidas())
                .nota(domain.nota())
                .tituloDaResenha(domain.tituloDaResenha())
                .resenha(domain.resenha())
                .spoiler(domain.spoiler())
                .build();
    }

    public DiarioDeLeituraResponseDto toResponse(DiarioDeLeitura domain) {
        if (domain == null) return null;
        return new DiarioDeLeituraResponseDto(
                domain.id(),
                null,
                domain.inicioDaLeitura(),
                domain.terminoDaLeitura(),
                domain.comentarios() != null
                        ? domain.comentarios().stream()
                            .map(c -> new AcompanhamentoLeituraResponseDto(
                                    c.id(), c.paginaInicial(), c.paginaFinal(), c.comentario()))
                            .toList()
                        : new ArrayList<>(),
                domain.nota(),
                domain.tituloDaResenha(),
                domain.resenha(),
                domain.spoiler()
        );
    }

    public AcompanhamentoDeLeitura toDomainAcompanhamento(AcompanhamentoDeLeituraEntity entity) {
        if (entity == null) return null;
            DiarioDeLeitura diario = null;
            if (entity.getDiarioDeLeitura() != null) {
                var d = entity.getDiarioDeLeitura();
                Leitura leitura = null;
                if (d.getLeitura() != null) {
                    var l = d.getLeitura();
                    Usuario usuario = l.getUsuario() != null
                            ? new Usuario(l.getUsuario().getId(), null, null, null, null, null,
                                    null, null, null, null, null, null, null, null)
                            : null;
                    leitura = new Leitura(l.getId(), null, usuario, null, null, null);
                }
                diario = new DiarioDeLeitura(d.getId(), leitura, null, null, null, null, null, null, null, null);
            }
            return new AcompanhamentoDeLeitura(entity.getId(),
                    entity.getPaginaInicial(), entity.getPaginaFinal(),
                    entity.getComentario(), diario);
    }

    public AcompanhamentoDeLeituraEntity toPersistence(AcompanhamentoDeLeitura domain) {
        if (domain == null) return null;
        return AcompanhamentoDeLeituraEntity.builder()
                .id(domain.id())
                .paginaInicial(domain.paginaInicial())
                .paginaFinal(domain.paginaFinal())
                .comentario(domain.comentario())
                .build();
    }
}
