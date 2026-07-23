package com.usuario.quero_ler.infrastructure.config;

import com.usuario.quero_ler.core.gateway.*;
import com.usuario.quero_ler.core.utils.PasswordHasher;
import com.usuario.quero_ler.core.usecases.acompanhamento.impl.*;
import com.usuario.quero_ler.infrastructure.security.BCryptPasswordHasher;
import com.usuario.quero_ler.core.usecases.autor.impl.CriarAutorUseCaseImpl;
import com.usuario.quero_ler.core.usecases.diario.impl.AtualizarDiarioUseCaseImpl;
import com.usuario.quero_ler.core.usecases.diario.impl.BuscarDiarioPorLivroEUsuarioUseCaseImpl;
import com.usuario.quero_ler.core.usecases.diario.impl.CriarDiarioUseCaseImpl;
import com.usuario.quero_ler.core.usecases.documento.impl.AlterarDocumentoUseCaseImpl;
import com.usuario.quero_ler.core.usecases.documento.impl.BuscarTermosGeraisDeUsoUseCaseImpl;
import com.usuario.quero_ler.core.usecases.documento.impl.CriarDocumentoUseCaseImpl;
import com.usuario.quero_ler.core.usecases.leitura.impl.AdicionarLeituraUseCaseImpl;
import com.usuario.quero_ler.core.usecases.leitura.impl.ControleStatusLeituraUseCaseImpl;
import com.usuario.quero_ler.core.usecases.leitura.impl.RemoverLeituraUseCaseImpl;
import com.usuario.quero_ler.core.usecases.livro.impl.*;
import com.usuario.quero_ler.core.usecases.meta.impl.CriarMetaUseCaseImpl;
import com.usuario.quero_ler.core.usecases.meta.impl.DeletarMetasUseCaseImpl;
import com.usuario.quero_ler.core.usecases.notificacao.impl.ApagarNotificacoesAntigasUseCaseImpl;
import com.usuario.quero_ler.core.usecases.notificacao.impl.CriarNotificacaoUseCaseImpl;
import com.usuario.quero_ler.core.usecases.notificacao.impl.ListarNotificacoesPorUsuarioUseCaseImpl;
import com.usuario.quero_ler.core.usecases.notificacao.impl.MarcarNotificacoesComoLidasUseCaseImpl;
import com.usuario.quero_ler.core.usecases.user.impl.AlterarSenhaUseCaseImpl;
import com.usuario.quero_ler.core.usecases.user.impl.AtualizarUserUseCaseImpl;
import com.usuario.quero_ler.core.usecases.user.impl.CriarUserUseCaseImpl;
import com.usuario.quero_ler.core.usecases.user.impl.LoginUseCaseImpl;
import com.usuario.quero_ler.core.usecases.usuario.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    PasswordHasher passwordHasher() {
        return new BCryptPasswordHasher();
    }

    @Bean
    ControleStatusLeituraUseCaseImpl controleStatusLeituraUseCase() {
        return new ControleStatusLeituraUseCaseImpl();
    }

    @Bean
    CriarAutorUseCaseImpl criarAutorUseCase(AutorGateway autorGateway) {
        return new CriarAutorUseCaseImpl(autorGateway);
    }

    @Bean
    AdicionarComentarioUseCaseImpl adicionarComentarioUseCase(
            AcompanhamentoDeLeituraGateway acompanhamentoGateway,
            DiarioDeLeituraGateway diarioGateway) {
        return new AdicionarComentarioUseCaseImpl(acompanhamentoGateway, diarioGateway);
    }

    @Bean
    ListarAcompanhamentoPorLivroUseCaseImpl listarAcompanhamentoPorLivroUseCase(
            AcompanhamentoDeLeituraGateway acompanhamentoGateway) {
        return new ListarAcompanhamentoPorLivroUseCaseImpl(acompanhamentoGateway);
    }

    @Bean
    ListarAcompanhamentoPorUsuarioUseCaseImpl listarAcompanhamentoPorUsuarioUseCase(
            AcompanhamentoDeLeituraGateway acompanhamentoGateway) {
        return new ListarAcompanhamentoPorUsuarioUseCaseImpl(acompanhamentoGateway);
    }

    @Bean
    CriarDiarioUseCaseImpl criarDiarioUseCase(
            DiarioDeLeituraGateway diarioGateway,
            LeituraGateway leituraGateway,
            ControleStatusLeituraUseCaseImpl controleStatusUseCase) {
        return new CriarDiarioUseCaseImpl(diarioGateway, leituraGateway, controleStatusUseCase);
    }

    @Bean
    AtualizarDiarioUseCaseImpl atualizarDiarioUseCase(
            DiarioDeLeituraGateway diarioGateway,
            LeituraGateway leituraGateway,
            ControleStatusLeituraUseCaseImpl controleStatusUseCase) {
        return new AtualizarDiarioUseCaseImpl(diarioGateway, leituraGateway, controleStatusUseCase);
    }

    @Bean
    BuscarDiarioPorLivroEUsuarioUseCaseImpl buscarDiarioPorLivroEUsuarioUseCase(
            DiarioDeLeituraGateway diarioGateway) {
        return new BuscarDiarioPorLivroEUsuarioUseCaseImpl(diarioGateway);
    }

    @Bean
    AlterarDocumentoUseCaseImpl alterarDocumentoUseCase(
            DocumentoGateway documentoGateway,
            NotificacaoGateway notificacaoGateway,
            UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        return new AlterarDocumentoUseCaseImpl(documentoGateway, notificacaoGateway, usuarioNotificacaoGateway);
    }

    @Bean
    BuscarTermosGeraisDeUsoUseCaseImpl buscarTermosGeraisDeUsoUseCase(
            DocumentoGateway documentoGateway) {
        return new BuscarTermosGeraisDeUsoUseCaseImpl(documentoGateway);
    }

    @Bean
    CriarDocumentoUseCaseImpl criarDocumentoUseCase(
            DocumentoGateway documentoGateway,
            NotificacaoGateway notificacaoGateway,
            UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        return new CriarDocumentoUseCaseImpl(documentoGateway, notificacaoGateway, usuarioNotificacaoGateway);
    }

    @Bean
    AdicionarLeituraUseCaseImpl adicionarLeituraUseCase(
            LeituraGateway leituraGateway,
            BuscarLivroUseCaseImpl buscarLivroUseCase,
            ControleStatusLeituraUseCaseImpl controleStatusUseCase) {
        return new AdicionarLeituraUseCaseImpl(leituraGateway, buscarLivroUseCase, controleStatusUseCase);
    }

    @Bean
    RemoverLeituraUseCaseImpl removerLeituraUseCase(LeituraGateway leituraGateway) {
        return new RemoverLeituraUseCaseImpl(leituraGateway);
    }

    @Bean
    BuscarCapaLivroUseCaseImpl buscarCapaLivroUseCase(LivroGateway livroGateway) {
        return new BuscarCapaLivroUseCaseImpl(livroGateway);
    }

    @Bean
    BuscarLivroPorIsbnUseCaseImpl buscarLivroPorIsbnUseCase(LivroGateway livroGateway) {
        return new BuscarLivroPorIsbnUseCaseImpl(livroGateway);
    }

    @Bean
    BuscarLivroUseCaseImpl buscarLivroUseCase(LivroGateway livroGateway) {
        return new BuscarLivroUseCaseImpl(livroGateway);
    }

    @Bean
    BuscarLivrosComFiltrosUseCaseImpl buscarLivrosComFiltrosUseCase(LivroGateway livroGateway) {
        return new BuscarLivrosComFiltrosUseCaseImpl(livroGateway);
    }

    @Bean
    CriarLivroUseCaseImpl criarLivroUseCase(LivroGateway livroGateway, CriarAutorUseCaseImpl criarAutorUseCase) {
        return new CriarLivroUseCaseImpl(livroGateway, criarAutorUseCase);
    }

    @Bean
    InserirCapaLivroUseCaseImpl inserirCapaLivroUseCase(LivroGateway livroGateway) {
        return new InserirCapaLivroUseCaseImpl(livroGateway);
    }

    @Bean
    ListarLivrosDetalhadosUseCaseImpl listarLivrosDetalhadosUseCase(LeituraGateway leituraGateway) {
        return new ListarLivrosDetalhadosUseCaseImpl(leituraGateway);
    }

    @Bean
    ListarLivrosPopularesUseCaseImpl listarLivrosPopularesUseCase(LivroGateway livroGateway) {
        return new ListarLivrosPopularesUseCaseImpl(livroGateway);
    }

    @Bean
    ListarLivrosTelaLeituraUseCaseImpl listarLivrosTelaLeituraUseCase(LeituraGateway leituraGateway) {
        return new ListarLivrosTelaLeituraUseCaseImpl(leituraGateway);
    }

    @Bean
    CriarMetaUseCaseImpl criarMetaUseCase(MetaLeituraGateway metaLeituraGateway) {
        return new CriarMetaUseCaseImpl(metaLeituraGateway);
    }

    @Bean
    DeletarMetasUseCaseImpl deletarMetasUseCase(MetaLeituraGateway metaLeituraGateway) {
        return new DeletarMetasUseCaseImpl(metaLeituraGateway);
    }

    @Bean
    ApagarNotificacoesAntigasUseCaseImpl apagarNotificacoesAntigasUseCase(
            UsuarioNotificacaoGateway usuarioNotificacaoGateway,
            NotificacaoGateway notificacaoGateway) {
        return new ApagarNotificacoesAntigasUseCaseImpl(usuarioNotificacaoGateway, notificacaoGateway);
    }

    @Bean
    CriarNotificacaoUseCaseImpl criarNotificacaoUseCase(
            NotificacaoGateway notificacaoGateway,
            UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        return new CriarNotificacaoUseCaseImpl(notificacaoGateway, usuarioNotificacaoGateway);
    }

    @Bean
    ListarNotificacoesPorUsuarioUseCaseImpl listarNotificacoesPorUsuarioUseCase(
            UsuarioNotificacaoGateway usuarioNotificacaoGateway,
            ApagarNotificacoesAntigasUseCaseImpl apagarNotificacoesAntigasUseCase) {
        return new ListarNotificacoesPorUsuarioUseCaseImpl(usuarioNotificacaoGateway, apagarNotificacoesAntigasUseCase);
    }

    @Bean
    MarcarNotificacoesComoLidasUseCaseImpl marcarNotificacoesComoLidasUseCase(
            UsuarioNotificacaoGateway usuarioNotificacaoGateway,
            ApagarNotificacoesAntigasUseCaseImpl apagarNotificacoesAntigasUseCase) {
        return new MarcarNotificacoesComoLidasUseCaseImpl(usuarioNotificacaoGateway, apagarNotificacoesAntigasUseCase);
    }

    @Bean
    AlterarSenhaUseCaseImpl alterarSenhaUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new AlterarSenhaUseCaseImpl(userGateway, passwordHasher);
    }

    @Bean
    AtualizarUserUseCaseImpl atualizarUserUseCase(UserGateway userGateway) {
        return new AtualizarUserUseCaseImpl(userGateway);
    }

    @Bean
    CriarUserUseCaseImpl criarUserUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new CriarUserUseCaseImpl(userGateway, passwordHasher);
    }

    @Bean
    LoginUseCaseImpl loginUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new LoginUseCaseImpl(userGateway, passwordHasher);
    }

    @Bean
    AtualizarDadosAdicionaisUseCaseImpl atualizarDadosAdicionaisUseCase(UsuarioGateway usuarioGateway) {
        return new AtualizarDadosAdicionaisUseCaseImpl(usuarioGateway);
    }

    @Bean
    AtualizarDadosAdministradorUseCaseImpl atualizarDadosAdministradorUseCase(UsuarioGateway usuarioGateway) {
        return new AtualizarDadosAdministradorUseCaseImpl(usuarioGateway);
    }

    @Bean
    AtualizarDadosLeitorUseCaseImpl atualizarDadosLeitorUseCase(
            UsuarioGateway usuarioGateway, UserGateway userGateway) {
        return new AtualizarDadosLeitorUseCaseImpl(usuarioGateway, userGateway);
    }

    @Bean
    AtualizarEmailUsuarioUseCaseImpl atualizarEmailUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new AtualizarEmailUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    BuscarFotoUsuarioUseCaseImpl buscarFotoUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarFotoUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    BuscarUsuarioUseCaseImpl buscarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    CriarUsuarioUseCaseImpl criarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new CriarUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    ExcluirPerfilUseCaseImpl excluirPerfilUseCase(
            UsuarioGateway usuarioGateway, UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        return new ExcluirPerfilUseCaseImpl(usuarioGateway, usuarioNotificacaoGateway);
    }
}
