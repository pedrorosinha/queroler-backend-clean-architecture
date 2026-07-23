package com.usuario.quero_ler.core.utils;

import java.util.List;

public record PaginatedResult<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int page,
        int size
) {
    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }
}
