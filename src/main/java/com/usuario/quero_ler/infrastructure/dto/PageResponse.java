package com.usuario.quero_ler.infrastructure.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        long totalElements,
        int totalPages
) {}
