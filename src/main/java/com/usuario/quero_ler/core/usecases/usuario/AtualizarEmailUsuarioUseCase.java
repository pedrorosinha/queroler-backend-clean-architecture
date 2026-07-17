package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;

public interface AtualizarEmailUsuarioUseCase {
    Usuario execute(Usuario usuario, String novoEmail);
}
