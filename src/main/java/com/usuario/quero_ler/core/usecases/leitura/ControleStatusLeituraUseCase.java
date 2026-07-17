package com.usuario.quero_ler.core.usecases.leitura;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;

public interface ControleStatusLeituraUseCase {
    Leitura execute(Leitura leitura, LeituraStatus status);
}
