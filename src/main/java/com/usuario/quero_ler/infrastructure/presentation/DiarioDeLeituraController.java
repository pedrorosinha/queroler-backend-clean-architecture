package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.diario.CriarDiarioUseCase;
import com.usuario.quero_ler.core.usecases.diario.BuscarDiarioPorLivroEUsuarioUseCase;
import com.usuario.quero_ler.core.usecases.diario.AtualizarDiarioUseCase;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraRequestDto;
import com.usuario.quero_ler.infrastructure.dto.leitura.DiarioDeLeituraAtualizadoRequest;
import com.usuario.quero_ler.infrastructure.mapper.DiarioLeituraMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diario")
public class DiarioDeLeituraController {

    private final CriarDiarioUseCase criarDiarioUseCase;
    private final BuscarDiarioPorLivroEUsuarioUseCase buscarDiarioPorLivroEUsuarioUseCase;
    private final AtualizarDiarioUseCase atualizarDiarioUseCase;
    private final DiarioLeituraMapper diarioLeituraMapper;
    private final UsuarioAtualHelper usuarioAtual;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid DiarioDeLeituraRequestDto dto) {
        var diario = diarioLeituraMapper.toDomain(dto);
        criarDiarioUseCase.execute(diario, usuarioAtual.getUsuarioAtualId(), dto.livroId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> buscarDiarioDeLeitura(@RequestParam Long livroId) {
        var diario = buscarDiarioPorLivroEUsuarioUseCase.execute(usuarioAtual.getUsuarioAtualId(), livroId);
        return ResponseEntity.ok(diarioLeituraMapper.toResponse(diario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id,
            @RequestBody DiarioDeLeituraAtualizadoRequest dto) {
        var dadosAtualizados = diarioLeituraMapper.toDomain(dto);
        atualizarDiarioUseCase.execute(id, dadosAtualizados, usuarioAtual.getUsuarioAtualId());
        return ResponseEntity.noContent().build();
    }
}
