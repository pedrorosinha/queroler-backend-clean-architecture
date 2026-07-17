package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;

public interface AtualizarDadosAdicionaisUseCase {
    Usuario execute(Usuario usuario, String cidade, String estado, String pais, byte[] foto);
}
