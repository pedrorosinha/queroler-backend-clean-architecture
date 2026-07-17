package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoLeituraResponseDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraRequestDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraResponseDto;
import com.usuario.quero_ler.infrastructure.dto.livro.LivroResumoResponseDto;
import com.usuario.quero_ler.core.entities.DiarioDeLeitura;

public final class DiarioLeituraFixtures {

    private DiarioLeituraFixtures() {
    }

    public static DiarioDeLeituraRequestDto novoDiarioDeLeitura() {
        return new DiarioDeLeituraRequestDto(
                2L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                10,
                4.0,
                "Titulo",
                "resenha",
                true);
    }

    public static DiarioDeLeitura diarioDeLeitura() {
        return EntityBuilders.diarioDeLeitura()
                .id(1L)
                .inicioDaLeitura(LocalDateTime.now().minusDays(1))
                .terminoDaLeitura(LocalDateTime.now())
                .paginasLidas(10)
                .nota(4.0)
                .tituloDaResenha("Titulo")
                .resenha("resenha")
                .spoiler(true)
                .build();
    }

    public static DiarioDeLeituraResponseDto diarioDeLeituraResponse(){
        LivroResumoResponseDto livroResumo = new LivroResumoResponseDto(1L, "titulo", 10);
        List<AcompanhamentoLeituraResponseDto> acompanhamentoLeitura = new ArrayList<>();

        return new DiarioDeLeituraResponseDto(
                2L,
                livroResumo,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                acompanhamentoLeitura,
                0.0,
                "Titulo",
                "resenha",
                true
        );
    }
}
