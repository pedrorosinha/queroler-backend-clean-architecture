package com.usuario.quero_ler.infrastructure.presentation;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.usecases.meta.CriarMetaUseCase;
import com.usuario.quero_ler.core.usecases.meta.DeletarMetasUseCase;
import com.usuario.quero_ler.infrastructure.bean.UsuarioAtualHelper;
import com.usuario.quero_ler.infrastructure.dto.meta.MetaRequestDto;
import com.usuario.quero_ler.infrastructure.mapper.MetaLeituraMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/metas")
public class MetaController {

    private final CriarMetaUseCase criarMetaUseCase;
    private final DeletarMetasUseCase deletarMetasUseCase;
    private final MetaLeituraMapper metaLeituraMapper;
    private final UsuarioAtualHelper usuarioAtual;

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody @Valid MetaRequestDto dto) {
        MetaLeitura meta = metaLeituraMapper.toDomain(dto);
        criarMetaUseCase.execute(meta, usuarioAtual.getUsuarioAtual());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar() {
        deletarMetasUseCase.execute(usuarioAtual.getUsuarioAtual());
        return ResponseEntity.noContent().build();
    }
}
