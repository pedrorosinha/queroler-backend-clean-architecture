package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.LivroIdioma;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public record Livro(
    Long id,
    String titulo,
    String isbn,
    String editora,
    Year anoDePublicacao,
    Integer numeroDePaginas,
    LivroIdioma idioma,
    String sinopse,
    byte[] capaDoLivro,
    List<Autor> autores,
    List<Leitura> usuarios,
    LocalDateTime dataDeCadastro,
    Integer quantidadeDeUso) {
  public Livro {
    autores = autores == null ? new ArrayList<>() : new ArrayList<>(autores);
    usuarios = usuarios == null ? new ArrayList<>() : new ArrayList<>(usuarios);
    dataDeCadastro = dataDeCadastro == null ? LocalDateTime.now() : dataDeCadastro;
    quantidadeDeUso = quantidadeDeUso == null ? 0 : quantidadeDeUso;
  }

  public Livro withCapaDoLivro(byte[] capaDoLivro) {
    return new Livro(id, titulo, isbn, editora, anoDePublicacao, numeroDePaginas, idioma, sinopse, capaDoLivro, autores,
        usuarios, dataDeCadastro, quantidadeDeUso);
  }

  public Livro withAutores(List<Autor> autores) {
    return new Livro(id, titulo, isbn, editora, anoDePublicacao, numeroDePaginas, idioma, sinopse, capaDoLivro,
        autores, usuarios, dataDeCadastro, quantidadeDeUso);
  }

  public Livro incrementarUso() {
    return new Livro(id, titulo, isbn, editora, anoDePublicacao, numeroDePaginas, idioma, sinopse, capaDoLivro, autores,
        usuarios, dataDeCadastro, quantidadeDeUso + 1);
  }
}
