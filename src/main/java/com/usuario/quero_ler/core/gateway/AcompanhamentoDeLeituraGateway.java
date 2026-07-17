package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;

import java.util.List;

public interface AcompanhamentoDeLeituraGateway {
    List<AcompanhamentoDeLeitura> findByLivroIdWithJoins(Long livroId);

    List<AcompanhamentoDeLeitura> findByUsuarioIdWithJoins(Long usuarioId);

    AcompanhamentoDeLeitura save(AcompanhamentoDeLeitura acompanhamento);
}
