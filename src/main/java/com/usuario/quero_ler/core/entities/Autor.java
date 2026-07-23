package com.usuario.quero_ler.core.entities;

import java.util.ArrayList;
import java.util.List;

public record Autor(
    Long id,
    String nome,
    List<Livro> livros) {
  public Autor {
    livros = livros == null ? new ArrayList<>() : new ArrayList<>(livros);
  }
}
