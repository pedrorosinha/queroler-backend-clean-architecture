package com.usuario.quero_ler.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Usuario(
        Long id,
        String nome,
        String email,
        String cpf,
        LocalDate dataDeNascimento,
        Boolean aceitarTermos,
        String cidade,
        String estado,
        String pais,
        byte[] foto,
        User user,
        List<UsuarioNotificacao> notificacoes,
        List<Leitura> livros,
        List<MetaLeitura> metasLeitura) {
    public Usuario {
        notificacoes = notificacoes == null ? new ArrayList<>() : new ArrayList<>(notificacoes);
        livros = livros == null ? new ArrayList<>() : new ArrayList<>(livros);
        metasLeitura = metasLeitura == null ? new ArrayList<>() : new ArrayList<>(metasLeitura);
    }

    public Usuario withCidade(String cidade) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withEstado(String estado) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withPais(String pais) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withEmail(String email) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withNome(String nome) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withDataDeNascimento(LocalDate dataDeNascimento) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withAceitarTermos(Boolean aceitarTermos) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withFoto(byte[] foto) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }

    public Usuario withUser(User user) {
        return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos, cidade, estado, pais, foto, user,
                notificacoes, livros, metasLeitura);
    }
}
