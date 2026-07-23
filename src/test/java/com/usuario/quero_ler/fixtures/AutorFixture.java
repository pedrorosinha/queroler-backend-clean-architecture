package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;

import com.usuario.quero_ler.infrastructure.dto.autor.AutorRequest;
import com.usuario.quero_ler.infrastructure.dto.autor.AutorResponse;
import com.usuario.quero_ler.core.entities.Autor;

public class AutorFixture {
    private static final Long ID = 1L;
    private static final String NOME = "Robert C. Martin";

    public static AutorRequest request() {
        return new AutorRequest(NOME);
    }

    public static Autor entity() {
        return autor()
                .id(ID)
                .nome(NOME)
                .build();
    }

    public static AutorResponse response() {
        return new AutorResponse(ID, NOME);
    }
}
