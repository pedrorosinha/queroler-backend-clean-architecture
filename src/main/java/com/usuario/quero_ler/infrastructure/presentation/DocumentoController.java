package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.usecases.documento.CriarDocumentoUseCase;
import com.usuario.quero_ler.core.usecases.documento.BuscarTermosGeraisDeUsoUseCase;
import com.usuario.quero_ler.core.usecases.documento.AlterarDocumentoUseCase;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoAlteracoesDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoRequestDto;
import com.usuario.quero_ler.infrastructure.mapper.DocumentoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final CriarDocumentoUseCase criarDocumentoUseCase;
    private final BuscarTermosGeraisDeUsoUseCase buscarTermosGeraisDeUsoUseCase;
    private final AlterarDocumentoUseCase alterarDocumentoUseCase;
    private final DocumentoMapper documentoMapper;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid DocumentoRequestDto dto) {
        var doc = documentoMapper.toDomain(dto);
        var criado = criarDocumentoUseCase.execute(doc);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoMapper.toResponse(criado));
    }

    @GetMapping("/termos-gerais-de-uso")
    public ResponseEntity<?> termosGeraisDeUso() {
        var doc = buscarTermosGeraisDeUsoUseCase.execute();
        return ResponseEntity.ok(documentoMapper.toResponse(doc));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable Long id,
            @RequestBody @Valid DocumentoAlteracoesDto dto) {
        var atualizado = documentoMapper.toDomain(dto);
        alterarDocumentoUseCase.execute(id, atualizado);
        return ResponseEntity.noContent().build();
    }
}
