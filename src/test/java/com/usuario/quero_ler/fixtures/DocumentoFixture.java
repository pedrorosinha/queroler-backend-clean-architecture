package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;

import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoRequestDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoResponseDto;
import com.usuario.quero_ler.core.enums.DocumentoTipo;
import com.usuario.quero_ler.core.entities.Documento;

import java.time.LocalDateTime;

public class DocumentoFixture {
    private static final Long ID = 2L;
    private static final String TITULO = "TERMOS_GERAIS_DE_USO";
    private static final DocumentoTipo TIPO = DocumentoTipo.TERMOS_GERAIS_DE_USO;
    private static final String CONTEUDO = "Conteúdo do documento";
    private static final LocalDateTime ULTIMA_ALTERACAO = LocalDateTime.now();

    public static DocumentoRequestDto requestDto(){
        return new DocumentoRequestDto(TITULO, TIPO, CONTEUDO);
    }

    public static Documento entity(){
        return documento()
                .id(ID)
                .titulo(TITULO)
                .tipo(TIPO)
                .conteudo(CONTEUDO)
                .ultimaAlteracao(ULTIMA_ALTERACAO)
                .build();
    }

    public static DocumentoResponseDto responseDto(){
        return new DocumentoResponseDto(ID, TITULO, TIPO, CONTEUDO, ULTIMA_ALTERACAO);
    }
}
