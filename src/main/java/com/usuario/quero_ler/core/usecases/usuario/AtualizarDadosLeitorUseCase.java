package com.usuario.quero_ler.core.usecases.usuario;

import com.usuario.quero_ler.core.entities.Usuario;

public interface AtualizarDadosLeitorUseCase {
    void execute(Usuario usuario, String novoEmail, String nome, java.time.LocalDate dataDeNascimento,
                 String cidade, String estado, String pais, byte[] foto);
}
