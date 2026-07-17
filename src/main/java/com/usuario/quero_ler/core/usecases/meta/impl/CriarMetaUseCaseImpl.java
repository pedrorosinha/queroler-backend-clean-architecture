package com.usuario.quero_ler.core.usecases.meta.impl;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.gateway.MetaLeituraGateway;
import com.usuario.quero_ler.core.usecases.meta.CriarMetaUseCase;
import com.usuario.quero_ler.core.exceptions.DataInvalidaException;
import com.usuario.quero_ler.core.exceptions.MetaDeLeituraJaCadastradaException;

import java.time.LocalDate;

public class CriarMetaUseCaseImpl implements CriarMetaUseCase {

    private final MetaLeituraGateway metaLeituraGateway;

    public CriarMetaUseCaseImpl(MetaLeituraGateway metaLeituraGateway) {
        this.metaLeituraGateway = metaLeituraGateway;
    }

    @Override
    public MetaLeitura execute(MetaLeitura meta, Usuario usuario) {
        Integer anoAtual = LocalDate.now().getYear();
        Integer anoMeta = meta.ano() != null ? meta.ano() : anoAtual;

        if (anoMeta < anoAtual) {
            throw new DataInvalidaException("O ano informado não pode ser anterior ao corrente.");
        }

        if (metaLeituraGateway.existsByUsuarioAndAno(usuario, anoMeta)) {
            throw new MetaDeLeituraJaCadastradaException(
                    "Já há meta cadastrada para o ano de: " + anoMeta + ".");
        }

        MetaLeitura novaMeta = meta.withUsuario(usuario);
        return metaLeituraGateway.save(novaMeta);
    }
}
