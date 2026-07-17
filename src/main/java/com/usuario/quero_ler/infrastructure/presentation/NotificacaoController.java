package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.notificacao.ListarNotificacoesPorUsuarioUseCase;
import com.usuario.quero_ler.core.usecases.notificacao.MarcarNotificacoesComoLidasUseCase;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.notificacao.NotificacaoResponseDto;
import com.usuario.quero_ler.infrastructure.mapper.NotificacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final ListarNotificacoesPorUsuarioUseCase listarNotificacoesPorUsuarioUseCase;
    private final MarcarNotificacoesComoLidasUseCase marcarNotificacoesComoLidasUseCase;
    private final NotificacaoMapper notificacaoMapper;
    private final UsuarioAtualHelper usuarioAtual;

    @GetMapping
    public ResponseEntity<?> naoLidas(Pageable pageable) {
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        var notificacoes = listarNotificacoesPorUsuarioUseCase.execute(usuarioAtual.getUsuarioAtualId(), pagination);
        var response = notificacoes.content().stream().map(notificacaoMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> marcarComoLidas() {
        marcarNotificacoesComoLidasUseCase.execute(usuarioAtual.getUsuarioAtualId());
        return ResponseEntity.noContent().build();
    }
}
