package com.usuario.quero_ler.infrastructure.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usuario.quero_ler.core.enums.UsuarioProfile;

public record UsuarioResponseDto(
        Long id,
        String nome,
        String email,
        String cpf,
        UsuarioProfile profile,
        @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "31/12/1990")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataDeNascimento,
        Boolean checkTermo,
        String cidade,
        String estado,
        String pais,
        String fotoUrl
) {
}