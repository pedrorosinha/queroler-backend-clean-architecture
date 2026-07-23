package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;

import java.util.Optional;

public interface DiarioDeLeituraGateway {
    boolean existsByLeitura(Leitura leitura);

    Optional<DiarioDeLeitura> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId);

    Optional<DiarioDeLeitura> findById(Long id);

    DiarioDeLeitura save(DiarioDeLeitura diario);
}
