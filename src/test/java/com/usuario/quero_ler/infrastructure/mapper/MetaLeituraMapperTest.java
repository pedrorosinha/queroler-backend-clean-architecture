package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.infrastructure.dto.meta.MetaRequestDto;
import com.usuario.quero_ler.fixtures.MetaLeituraFixture;
import com.usuario.quero_ler.core.entities.MetaLeitura;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MetaLeituraMapperTest {

    @InjectMocks
    private MetaLeituraMapper mapper;

    @Test
    @DisplayName("Deve Converter uma meta leitura request em domain.")
    void toDomain() {
        Integer proximoAno = LocalDate.now().plusYears(1).getYear();
        MetaRequestDto dto = MetaLeituraFixture.requestDto(proximoAno);

        MetaLeitura meta = mapper.toDomain(dto);

        assertEquals(dto.ano(), meta.ano());
        assertEquals(dto.metaLivrosAno(), meta.metaLivrosAno());
        assertEquals(dto.metaLivrosMes(), meta.metaLivrosMes());
        assertEquals(dto.metaPaginasDia(), meta.metaPaginasDia());
    }

    @Test
    @DisplayName("Deve Converter uma meta leitura request em domain, com ano corrente.")
    void toDomainAnoCorrente() {
        Integer anoCorrente = LocalDate.now().getYear();
        MetaRequestDto dto = MetaLeituraFixture.requestDto(null);

        MetaLeitura meta = mapper.toDomain(dto);

        assertEquals(anoCorrente, meta.ano());
        assertEquals(dto.metaLivrosAno(), meta.metaLivrosAno());
        assertEquals(dto.metaLivrosMes(), meta.metaLivrosMes());
        assertEquals(dto.metaPaginasDia(), meta.metaPaginasDia());
    }
}
