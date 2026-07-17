package com.usuario.quero_ler.core.utils;

public record Pagination(int page, int size) {
    public static Pagination of(int page, int size) {
        return new Pagination(page, size);
    }
}
