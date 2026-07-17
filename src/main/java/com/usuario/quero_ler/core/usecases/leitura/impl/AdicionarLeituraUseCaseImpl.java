package com.usuario.quero_ler.core.usecases.leitura.impl;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.enums.LeituraStatus;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.usecases.leitura.AdicionarLeituraUseCase;
import com.usuario.quero_ler.core.usecases.leitura.ControleStatusLeituraUseCase;
import com.usuario.quero_ler.core.usecases.livro.BuscarLivroUseCase;
import com.usuario.quero_ler.core.exceptions.UsuarioJaPossueOLivroException;

import java.util.Optional;

public class AdicionarLeituraUseCaseImpl implements AdicionarLeituraUseCase {

    private final LeituraGateway leituraGateway;
    private final BuscarLivroUseCase buscarLivroUseCase;
    private final ControleStatusLeituraUseCase controleStatusUseCase;

    public AdicionarLeituraUseCaseImpl(LeituraGateway leituraGateway,
            BuscarLivroUseCase buscarLivroUseCase,
            ControleStatusLeituraUseCase controleStatusUseCase) {
        this.leituraGateway = leituraGateway;
        this.buscarLivroUseCase = buscarLivroUseCase;
        this.controleStatusUseCase = controleStatusUseCase;
    }

    @Override
    public void execute(Long idLivro, Long idUsuario, LeituraStatus status) {
        Optional<Leitura> usuarioLeitura = leituraGateway.findByUsuarioIdAndLivroId(idUsuario, idLivro);
        if (usuarioLeitura.isPresent()) {
            throw new UsuarioJaPossueOLivroException("O usuario já possue o livro na estante.");
        }

        Livro livro = buscarLivroUseCase.execute(idLivro);

        Usuario usuario = new Usuario(idUsuario, null, null, null, null, false,
                null, null, null, null, null, null, null, null);
        Leitura leitura = new Leitura(null, null, usuario, livro, null, null);

        leitura = controleStatusUseCase.execute(leitura, status);
        leitura = leitura.withStatus(status);

        leituraGateway.save(leitura);
    }
}
