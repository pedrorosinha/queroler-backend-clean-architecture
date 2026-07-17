package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;

public interface AtualizarDadosAdministradorUseCase {
    void execute(Usuario usuario, java.time.LocalDate dataDeNascimento,
                 String cidade, String estado, String pais, byte[] foto);
}
