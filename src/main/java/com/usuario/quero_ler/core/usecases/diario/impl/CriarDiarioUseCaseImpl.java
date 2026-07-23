package com.usuario.quero_ler.core.usecases.diario.impl;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.diario.CriarDiarioUseCase;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import com.usuario.quero_ler.core.exceptions.DiarioJaExisteException;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;
import com.usuario.quero_ler.core.exceptions.LeituraNaoEncontradaException;


import java.time.LocalDateTime;

public class CriarDiarioUseCaseImpl implements CriarDiarioUseCase {

    private final DiarioDeLeituraGateway diarioGateway;
    private final LeituraGateway leituraGateway;
    private final ControleStatusLeituraUseCase controleStatusUseCase;

    public CriarDiarioUseCaseImpl(DiarioDeLeituraGateway diarioGateway,
                                  LeituraGateway leituraGateway,
                                  ControleStatusLeituraUseCase controleStatusUseCase) {
        this.diarioGateway = diarioGateway;
        this.leituraGateway = leituraGateway;
        this.controleStatusUseCase = controleStatusUseCase;
    }

    @Override
    public void execute(DiarioDeLeitura diario, Long usuarioId, Long livroId) {
        validarCriacao(diario);

        Leitura leitura = leituraGateway.findByUsuarioIdAndLivroId(usuarioId, livroId)
                .orElseThrow(() -> new LeituraNaoEncontradaException(
                        "Usuário/Livro não encontrado na estante."));

        leitura = controleStatusUseCase.execute(leitura,
                diario.terminoDaLeitura() != null ? LeituraStatus.LIVROS_LIDOS : LeituraStatus.LIVROS_QUE_ESTOU_LENDO);
        leituraGateway.save(leitura);

        DiarioDeLeitura novoDiario = new DiarioDeLeitura(null, leitura,
                diario.inicioDaLeitura(), diario.terminoDaLeitura(), diario.paginasLidas(),
                null, diario.nota() != null ? diario.nota() : 0.0,
                diario.tituloDaResenha(), diario.spoiler() != null ? diario.spoiler() : false,
                diario.resenha());

        if (diarioGateway.existsByLeitura(leitura)) {
            throw new DiarioJaExisteException("Já existe um diário de leitura para este usuário e livro.");
        }

        diarioGateway.save(novoDiario);
    }

    private void validarCriacao(DiarioDeLeitura diario) {
        if (diario == null) {
            throw new DadosDiarioInvalidoException("Payload do diário está vazio.");
        }
        if (diario.inicioDaLeitura() == null) {
            throw new DadosDiarioInvalidoException("inicioDaLeitura é obrigatório.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (diario.inicioDaLeitura().isAfter(now)) {
            throw new DadosDiarioInvalidoException("inicioDaLeitura não pode estar no futuro.");
        }
        if (diario.terminoDaLeitura() != null && diario.terminoDaLeitura().isAfter(now)) {
            throw new DadosDiarioInvalidoException("terminoDaLeitura não pode estar no futuro.");
        }
        if (diario.terminoDaLeitura() != null && diario.inicioDaLeitura().isAfter(diario.terminoDaLeitura())) {
            throw new DadosDiarioInvalidoException("terminoDaLeitura não pode ser anterior a inicioDaLeitura.");
        }
        if (diario.paginasLidas() != null && diario.paginasLidas() < 0) {
            throw new DadosDiarioInvalidoException("paginasLidas não pode ser negativa.");
        }
        if (diario.nota() != null && (diario.nota() < 0 || diario.nota() > 5)) {
            throw new DadosDiarioInvalidoException("nota fora do intervalo permitido (0-5).");
        }
    }
}
