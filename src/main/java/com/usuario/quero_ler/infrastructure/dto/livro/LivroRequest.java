package com.usuario.quero_ler.infrastructure.dto.livro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.Year;
import java.util.List;

import com.usuario.quero_ler.core.enums.LivroIdioma;
import com.usuario.quero_ler.infrastructure.dto.autor.AutorRequest;

public record LivroRequest(
                @NotBlank String titulo,

                @NotBlank @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve conter apenas números e ter 10 ou 13 dígitos") String isbn,

                @NotBlank String editora,

                @NotNull(message = "O ano de publicação é obrigatório") @PastOrPresent(message = "O ano de publicação deve ser no passado ou presente") Year anoDePublicacao,

                @NotNull @Positive Integer numeroDePaginas,

                @NotNull LivroIdioma idioma,

                @NotBlank @Size(min = 50, max = 256) String sinopse,

                @NotEmpty @Valid List<AutorRequest> autores) {
}