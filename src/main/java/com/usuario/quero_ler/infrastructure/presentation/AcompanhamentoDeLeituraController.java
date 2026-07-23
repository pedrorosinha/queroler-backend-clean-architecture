package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.acompanhamento.AdicionarComentarioUseCase;
import com.usuario.quero_ler.infrastructure.dto.leitura.AcompanhamentoRequestDto;
import com.usuario.quero_ler.infrastructure.mapper.AcompanhamentoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leituras")
public class AcompanhamentoDeLeituraController {

    private final AdicionarComentarioUseCase adicionarComentarioUseCase;
    private final AcompanhamentoMapper acompanhamentoMapper;

    @PostMapping("/{diarioId}/comentarios")
    public ResponseEntity<Void> criarComentario(@PathVariable Long diarioId,
            @RequestBody @Valid AcompanhamentoRequestDto dto) {
        var acompanhamento = acompanhamentoMapper.toDomain(dto);
        adicionarComentarioUseCase.execute(diarioId, acompanhamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
