package com.usuario.quero_ler.core.usecases.diario.impl;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.diario.AtualizarDiarioUseCase;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import com.usuario.quero_ler.core.exceptions.DiarioNaoEncontradoException;
import com.usuario.quero_ler.core.exceptions.DadosDiarioInvalidoException;
import com.usuario.quero_ler.core.exceptions.UsuarioSemPermissaoParaAcaoException;


import java.time.LocalDateTime;

public class AtualizarDiarioUseCaseImpl implements AtualizarDiarioUseCase {

    private final DiarioDeLeituraGateway diarioGateway;
    private final LeituraGateway leituraGateway;
    private final ControleStatusLeituraUseCase controleStatusUseCase;

    public AtualizarDiarioUseCaseImpl(DiarioDeLeituraGateway diarioGateway,
                                      LeituraGateway leituraGateway,
                                      ControleStatusLeituraUseCase controleStatusUseCase) {
        this.diarioGateway = diarioGateway;
        this.leituraGateway = leituraGateway;
        this.controleStatusUseCase = controleStatusUseCase;
    }

    @Override
    public void execute(Long id, DiarioDeLeitura dadosAtualizados, Long usuarioId) {
        DiarioDeLeitura diario = diarioGateway.findById(id)
                .orElseThrow(() -> new DiarioNaoEncontradoException("Diário de leitura não encontrado."));

        verificarPropriedade(diario, usuarioId);
        validarAtualizacao(dadosAtualizados);

        LocalDateTime inicioEfetivo = dadosAtualizados.inicioDaLeitura() != null
                ? dadosAtualizados.inicioDaLeitura() : diario.inicioDaLeitura();
        LocalDateTime terminoEfetivo = dadosAtualizados.terminoDaLeitura() != null
                ? dadosAtualizados.terminoDaLeitura() : diario.terminoDaLeitura();

        if (inicioEfetivo != null && terminoEfetivo != null && inicioEfetivo.isAfter(terminoEfetivo)) {
            throw new DadosDiarioInvalidoException("terminoDaLeitura não pode ser anterior a inicioDaLeitura.");
        }

        if (dadosAtualizados.inicioDaLeitura() != null) {
            diario = diario.withInicioDaLeitura(dadosAtualizados.inicioDaLeitura());
        }
        if (dadosAtualizados.terminoDaLeitura() != null) {
            diario = diario.withTerminoDaLeitura(dadosAtualizados.terminoDaLeitura());
        }
        if (dadosAtualizados.paginasLidas() != null) {
            diario = diario.withPaginasLidas(dadosAtualizados.paginasLidas());
        }
        if (dadosAtualizados.nota() != null) {
            diario = diario.withNota(dadosAtualizados.nota());
        }
        if (dadosAtualizados.tituloDaResenha() != null) {
            diario = diario.withTituloDaResenha(dadosAtualizados.tituloDaResenha());
        }
        if (dadosAtualizados.resenha() != null) {
            diario = diario.withResenha(dadosAtualizados.resenha());
        }

        diarioGateway.save(diario);

        if (dadosAtualizados.terminoDaLeitura() != null) {
            Leitura leitura = diario.leitura();
            leitura = leitura.withLido(true);
            leitura = controleStatusUseCase.execute(leitura, LeituraStatus.LIVROS_LIDOS);
            leituraGateway.save(leitura);
        }
    }

    private void verificarPropriedade(DiarioDeLeitura diario, Long usuarioId) {
        if (diario.leitura() == null || diario.leitura().usuario() == null ||
                !diario.leitura().usuario().id().equals(usuarioId)) {
            throw new UsuarioSemPermissaoParaAcaoException("Usuário sem permissão para atualizar este diário.");
        }
    }

    private void validarAtualizacao(DiarioDeLeitura dados) {
        LocalDateTime now = LocalDateTime.now();
        if (dados.inicioDaLeitura() != null && dados.inicioDaLeitura().isAfter(now)) {
            throw new DadosDiarioInvalidoException("inicioDaLeitura não pode estar no futuro.");
        }
        if (dados.terminoDaLeitura() != null && dados.terminoDaLeitura().isAfter(now)) {
            throw new DadosDiarioInvalidoException("terminoDaLeitura não pode estar no futuro.");
        }
        if (dados.paginasLidas() != null && dados.paginasLidas() < 0) {
            throw new DadosDiarioInvalidoException("paginasLidas não pode ser negativa.");
        }
        if (dados.nota() != null && (dados.nota() < 0 || dados.nota() > 5)) {
            throw new DadosDiarioInvalidoException("nota fora do intervalo permitido (0-5).");
        }
    }
}
