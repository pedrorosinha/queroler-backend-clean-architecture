package com.usuario.quero_ler.core.usecases.acompanhamento;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;

import java.util.List;

public interface ListarAcompanhamentoPorUsuarioUseCase {
    List<AcompanhamentoDeLeitura> execute(Long usuarioId);
}
