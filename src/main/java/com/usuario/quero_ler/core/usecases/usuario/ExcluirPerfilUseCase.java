package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.enums.UsuarioProfile;

public interface ExcluirPerfilUseCase {
    void execute(Usuario usuario, UsuarioProfile profile);
}
