package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.leitura.AdicionarLeituraUseCase;
import com.usuario.quero_ler.core.usecases.leitura.RemoverLeituraUseCase;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.leitura.AdicionarLeituraRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leituras")
public class LeituraController {

    private final AdicionarLeituraUseCase adicionarLeituraUseCase;
    private final RemoverLeituraUseCase removerLeituraUseCase;
    private final UsuarioAtualHelper usuarioAtual;

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody @Valid AdicionarLeituraRequestDto dto) {
        adicionarLeituraUseCase.execute(dto.livroId(), usuarioAtual.getUsuarioAtualId(), dto.status());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{livroId}")
    public ResponseEntity<Void> remover(@PathVariable Long livroId) {
        removerLeituraUseCase.execute(livroId, usuarioAtual.getUsuarioAtualId());
        return ResponseEntity.noContent().build();
    }
}
