package com.usuario.quero_ler.core.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record DiarioDeLeitura(
        Long id,
        Leitura leitura,
        LocalDateTime inicioDaLeitura,
        LocalDateTime terminoDaLeitura,
        Integer paginasLidas,
        List<AcompanhamentoDeLeitura> comentarios,
        Double nota,
        String tituloDaResenha,
        Boolean spoiler,
        String resenha) {
    public DiarioDeLeitura {
        comentarios = comentarios == null ? new ArrayList<>() : new ArrayList<>(comentarios);
        nota = nota == null ? 0.0 : nota;
        spoiler = spoiler == null ? Boolean.FALSE : spoiler;
    }

    public DiarioDeLeitura withLeitura(Leitura leitura) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withInicioDaLeitura(LocalDateTime inicioDaLeitura) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withTerminoDaLeitura(LocalDateTime terminoDaLeitura) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withPaginasLidas(Integer paginasLidas) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withNota(Double nota) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withTituloDaResenha(String tituloDaResenha) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withSpoiler(Boolean spoiler) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withResenha(String resenha) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }

    public DiarioDeLeitura withComentarios(List<AcompanhamentoDeLeitura> comentarios) {
        return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura, paginasLidas, comentarios, nota,
                tituloDaResenha, spoiler, resenha);
    }
}
