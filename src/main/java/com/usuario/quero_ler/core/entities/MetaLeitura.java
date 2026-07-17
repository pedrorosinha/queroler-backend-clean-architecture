package com.usuario.quero_ler.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record MetaLeitura(
        Long id,
        Integer ano,
        Integer metaLivrosAno,
        Integer metaLivrosMes,
        Integer metaPaginasDia,
        Usuario usuario,
        List<LivroMeta> livrosMeta) {
    public MetaLeitura {
        livrosMeta = livrosMeta == null ? new ArrayList<>() : new ArrayList<>(livrosMeta);
    }

    public MetaLeitura withUsuario(Usuario usuario) {
        return new MetaLeitura(id, ano, metaLivrosAno, metaLivrosMes, metaPaginasDia, usuario, livrosMeta);
    }

    public boolean pertenceAoAnoAtual() {
        return ano.equals(LocalDate.now().getYear());
    }

    public boolean possuiAlgumaMeta() {
        return metaLivrosAno != null
                || metaLivrosMes != null
                || metaPaginasDia != null
                || !livrosMeta.isEmpty();
    }
}
