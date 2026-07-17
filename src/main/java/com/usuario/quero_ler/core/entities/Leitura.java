package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.LeituraStatus;

public record Leitura(
    Long id,
    LeituraStatus status,
    Usuario usuario,
    Livro livro,
    DiarioDeLeitura diarioDeLeitura,
    Boolean lido) {

  public Leitura withUsuario(Usuario usuario) {
    return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
  }

  public Leitura withLivro(Livro livro) {
    return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
  }

  public Leitura withStatus(LeituraStatus status) {
    return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
  }

  public Leitura withDiarioDeLeitura(DiarioDeLeitura diarioDeLeitura) {
    return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
  }

  public Leitura withLido(Boolean lido) {
    return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
  }
}
