package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;

public interface BuscarUsuarioUseCase {
    Usuario execute(Long id);
}
