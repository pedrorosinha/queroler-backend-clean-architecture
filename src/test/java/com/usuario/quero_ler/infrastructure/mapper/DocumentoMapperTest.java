package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoAlteracoesDto;
import com.usuario.quero_ler.infrastructure.dto.documento.DocumentoRequestDto;
import com.usuario.quero_ler.fixtures.DocumentoFixture;
import com.usuario.quero_ler.core.entities.Documento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentoMapperTest {

    @InjectMocks
    private DocumentoMapper mapper;

    @Test
    @DisplayName("Deve Converter o documento em domain.")
    void toDomain() {
        DocumentoRequestDto dto = DocumentoFixture.requestDto();

        Documento resposta = mapper.toDomain(dto);

        assertNull(resposta.id());
        assertEquals(dto.titulo(), resposta.titulo());
        assertEquals(dto.tipo(), resposta.tipo());
        assertEquals(dto.conteudo(), resposta.conteudo());

        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                resposta.ultimaAlteracao().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @DisplayName("Deve converter um documento domain em documento de resposta")
    void toResponse() {
        Documento documento = DocumentoFixture.entity();

        var resposta = mapper.toResponse(documento);

        assertEquals(documento.id(), resposta.id());
        assertEquals(documento.titulo(), resposta.titulo());
        assertEquals(documento.tipo(), resposta.tipo());
        assertEquals(documento.conteudo(), resposta.conteudo());

        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                resposta.ultimaAlteracao().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @DisplayName("Deve fazer a atualização do documento")
    void toUpdate() {
        DocumentoAlteracoesDto dto = new DocumentoAlteracoesDto("Titulo Alterado", null, "conteudo alterado");

        Documento resposta = mapper.toDomain(dto);

        assertNull(resposta.id());
        assertEquals(dto.titulo(), resposta.titulo());
        assertNull(resposta.tipo());
        assertEquals(dto.conteudo(), resposta.conteudo());
    }
}
