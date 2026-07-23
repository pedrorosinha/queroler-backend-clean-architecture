package com.usuario.quero_ler.core.entities;

import com.usuario.quero_ler.core.enums.DocumentoTipo;

import java.time.LocalDateTime;

public record Documento(
        Long id,
        String titulo,
        DocumentoTipo tipo,
        String conteudo,
        LocalDateTime ultimaAlteracao) {

    public Documento withTitulo(String titulo) {
        return new Documento(id, titulo, tipo, conteudo, ultimaAlteracao);
    }

    public Documento withTipo(DocumentoTipo tipo) {
        return new Documento(id, titulo, tipo, conteudo, ultimaAlteracao);
    }

    public Documento withConteudo(String conteudo) {
        return new Documento(id, titulo, tipo, conteudo, ultimaAlteracao);
    }

    public Documento withUltimaAlteracao(LocalDateTime ultimaAlteracao) {
        return new Documento(id, titulo, tipo, conteudo, ultimaAlteracao);
    }
}
