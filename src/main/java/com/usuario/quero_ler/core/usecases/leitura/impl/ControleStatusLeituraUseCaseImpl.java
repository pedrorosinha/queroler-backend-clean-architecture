package com.usuario.quero_ler.core.usecases.leitura.impl;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import com.usuario.quero_ler.core.exceptions.LeituraEstadoInvalidoException;
public class ControleStatusLeituraUseCaseImpl implements ControleStatusLeituraUseCase {

    @Override
    public Leitura execute(Leitura leitura, LeituraStatus status) {
        switch (leitura.status()) {
            case null:
                if (status.equals(LeituraStatus.LIVROS_QUE_QUERO_LER) ||
                        status.equals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO) ||
                        status.equals(LeituraStatus.LIVROS_LIDOS)) {
                    leitura = leitura.withStatus(status);
                    if (status.equals(LeituraStatus.LIVROS_LIDOS))
                        leitura = leitura.withLido(true);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente as transições Quero ler, lendo e lidos podem ser realizadas",
                            new RuntimeException());
                }
                break;
            case LIVROS_QUE_QUERO_LER:
                if (status.equals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO) ||
                        status.equals(LeituraStatus.LIVROS_ABANDONADOS) ||
                        status.equals(LeituraStatus.LIVROS_LIDOS)) {
                    leitura = leitura.withStatus(status);
                    if (status.equals(LeituraStatus.LIVROS_LIDOS))
                        leitura = leitura.withLido(true);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente as transições lendo, abandonados e lidos podem ser realizadas",
                            new RuntimeException());
                }
                break;
            case LIVROS_QUE_ESTOU_LENDO:
                if (status.equals(LeituraStatus.LIVROS_ABANDONADOS) ||
                        status.equals(LeituraStatus.LIVROS_LIDOS)) {
                    leitura = leitura.withStatus(status);
                    if (status.equals(LeituraStatus.LIVROS_LIDOS))
                        leitura = leitura.withLido(true);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente as transições abandonados e lidos podem ser realizadas",
                            new RuntimeException());
                }
                break;
            case RELENDO:
                if (status.equals(LeituraStatus.LIVROS_ABANDONADOS) ||
                        status.equals(LeituraStatus.LIVROS_LIDOS)) {
                    leitura = leitura.withStatus(status);
                    if (status.equals(LeituraStatus.LIVROS_LIDOS))
                        leitura = leitura.withLido(true);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente as transições abandonados e lidos podem ser realizadas",
                            new RuntimeException());
                }
                break;
            case LIVROS_ABANDONADOS:
                if (status.equals(LeituraStatus.LIVROS_QUE_ESTOU_LENDO) ||
                        (status.equals(LeituraStatus.LIVROS_QUE_QUERO_LER) &&
                                leitura.lido() == null)) {
                    leitura = leitura.withStatus(status);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente as transições estou lendo e quero ler podem ser realizadas",
                            new RuntimeException());
                }
                break;
            case LIVROS_LIDOS:
                if (status.equals(LeituraStatus.RELENDO)) {
                    leitura = leitura.withStatus(status);
                } else {
                    throw new LeituraEstadoInvalidoException(
                            "Transição inválida, para o estado atual somente a transições relendo pode ser realizada",
                            new RuntimeException());
                }
                break;
            default:
                throw new LeituraEstadoInvalidoException(
                        "Estado inexistente",
                        new RuntimeException());
        }
        return leitura;
    }
}
