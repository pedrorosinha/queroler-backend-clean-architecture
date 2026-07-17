package com.usuario.quero_ler.core.usecases.meta;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;

public interface CriarMetaUseCase {
    MetaLeitura execute(MetaLeitura meta, Usuario usuario);
}
